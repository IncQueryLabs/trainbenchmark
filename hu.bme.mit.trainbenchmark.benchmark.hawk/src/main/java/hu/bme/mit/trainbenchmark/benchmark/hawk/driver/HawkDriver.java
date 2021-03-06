/*******************************************************************************
 * Copyright (c) 2010-2015, Benedek Izso, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/
package hu.bme.mit.trainbenchmark.benchmark.hawk.driver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.apache.thrift.TException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.incquery.runtime.api.AdvancedIncQueryEngine;
import org.eclipse.incquery.runtime.api.IMatchUpdateListener;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;
import org.eclipse.incquery.runtime.api.impl.BasePatternMatch;
import org.eclipse.incquery.runtime.emf.EMFScope;

import hu.bme.mit.mondo.integration.incquery.hawk.HawkScope;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.driver.EMFIncQueryBaseDriver;
import hu.bme.mit.trainbenchmark.benchmark.hawk.config.HawkBenchmarkConfig;
import hu.bme.mit.trainbenchmark.railway.RailwayContainer;
import hu.bme.mit.trainbenchmark.railway.RailwayPackage;
import uk.ac.york.mondo.integration.api.Credentials;
import uk.ac.york.mondo.integration.api.Hawk.Client;
import uk.ac.york.mondo.integration.api.HawkInstance;
import uk.ac.york.mondo.integration.api.HawkInstanceNotFound;
import uk.ac.york.mondo.integration.api.HawkInstanceNotRunning;
import uk.ac.york.mondo.integration.api.Repository;
import uk.ac.york.mondo.integration.api.utils.APIUtils;
import uk.ac.york.mondo.integration.api.utils.APIUtils.ThriftProtocol;
import uk.ac.york.mondo.integration.hawk.emf.impl.HawkResourceFactoryImpl;
import uk.ac.york.mondo.integration.hawk.emf.impl.HawkResourceImpl;

public class HawkDriver<TMatch extends BasePatternMatch> extends EMFIncQueryBaseDriver<TMatch, HawkBenchmarkConfig> {

	private static final String ECORE_METAMODEL = "/hu.bme.mit.trainbenchmark.emf.model/model/railway.ecore";
	private static final String HAWK_REPOSITORY = "/models/hawkrepository/";
	private static final String PASSWORD = "admin";
	private static final String HAWK_INSTANCE = "trainbenchmark";
	private static final String HAWK_ADDRESS = "localhost:8080/thrift/hawk/tuple";
	private static final String HAWK_URL = "http://" + HAWK_ADDRESS;

	protected String hawkRepositoryPath;
	private Client client;
	private HawkResourceImpl hawkResource;

	public HawkDriver(final HawkBenchmarkConfig benchmarkConfig) {
		super(benchmarkConfig);
	}

	@Override
	public void initialize() throws Exception {
		super.initialize();

		final File workspaceRelativePath = new File(benchmarkConfig.getWorkspacePath());
		final String workspacePath = workspaceRelativePath.getAbsolutePath();

		hawkRepositoryPath = workspacePath + HAWK_REPOSITORY;
		cleanRepository(hawkRepositoryPath);
		connectToHawk(workspacePath);
	}

	protected void cleanRepository(final String hawkRepositoryPath) throws IOException {
		// remove the repository
		final File hawkRepositoryFile = new File(hawkRepositoryPath);
		FileUtils.deleteDirectory(hawkRepositoryFile);
	}

	protected void copyModelToHawk(final String hawkRepositoryPath, final String modelPath) throws IOException {
		final File modelFile = new File(modelPath);

		final File hawkRepositoryFile = new File(hawkRepositoryPath);
		FileUtils.deleteQuietly(hawkRepositoryFile);
		FileUtils.copyFileToDirectory(modelFile, hawkRepositoryFile);
	}

	protected void connectToHawk(final String workspacePath) throws Exception {
		client = APIUtils.connectToHawk(HAWK_URL, ThriftProtocol.TUPLE);
		try {
			client.startInstance(HAWK_INSTANCE, PASSWORD);
		} catch (final HawkInstanceNotFound ex) {
			client.createInstance(HAWK_INSTANCE, PASSWORD, 0, 0);
		}

		final String ecoreMetamodelPath = workspacePath + ECORE_METAMODEL;
		final java.io.File file = new java.io.File(ecoreMetamodelPath);
		final uk.ac.york.mondo.integration.api.File thriftFile = APIUtils.convertJavaFileToThriftFile(file);

		outer: do {
			final List<HawkInstance> listInstances = client.listInstances();
			for (final HawkInstance hi : listInstances) {
				if (HAWK_INSTANCE.equals(hi.getName()) && hi.isRunning()) {
					break outer;
				}
			}
			System.out.println("Waiting for Hawk to start.");
			Thread.sleep(500);
		} while (true);

		final ResourceSetImpl resourceSet = new ResourceSetImpl();
		final Registry resourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
		resourceFactoryRegistry.getProtocolToFactoryMap().put("hawk+http", new HawkResourceFactoryImpl());
		// set the Resource in the EMFDriver
		RailwayPackage.eINSTANCE.eClass();

		String hawkURI = "hawk+http://" + HAWK_ADDRESS + "?instance=" + HAWK_INSTANCE
				+ "&subscribe=true&durability=temporary&clientID=hu.trainbenchmark" + System.nanoTime();
		if (benchmarkConfig.isUseHawkScope()) {
			hawkURI += "&loadingMode=lazy_children";
		}
		
		resource = resourceSet.createResource(URI.createURI(hawkURI));
		resource.load(Collections.emptyMap());

		client.registerMetamodels(HAWK_INSTANCE, Arrays.asList(thriftFile));

		final Credentials credentials = new Credentials("dummy", "dummy");

		// We only create the repository if we don't have it already
		final Repository newRepository = new Repository(hawkRepositoryPath, "org.hawk.localfolder.LocalFolder");
		boolean bAlreadyExists = false;
		for (Repository r : client.listRepositories(HAWK_INSTANCE)) {
			if (r.equals(newRepository)) {
				bAlreadyExists = true;
				break;
			}
		}
		if (!bAlreadyExists) {
			client.addRepository(HAWK_INSTANCE, newRepository, credentials);
		}
	}

	@Override
	public void read(final String modelPathWithoutExtension) throws Exception {
		final String modelPath = benchmarkConfig.getModelPathWithoutExtension() + getPostfix();

		hawkResource = (HawkResourceImpl) resource;
		

		// copy the model to the hawk repository to allow Hawk to load the model
		copyModelToHawk(hawkRepositoryPath, modelPath);

		waitForSync();

		if (benchmarkConfig.isUseHawkScope()) {
			final HawkScope hawkScope = new HawkScope(hawkResource.getResourceSet(), client);
			engine = AdvancedIncQueryEngine.from(IncQueryEngine.on(hawkScope));
		} else {
			final EMFScope emfScope = new EMFScope(hawkResource.getResourceSet());
			engine = AdvancedIncQueryEngine.from(IncQueryEngine.on(emfScope));
		}
		
		final IncQueryMatcher<TMatch> matcher = checker.getMatcher();
		final Collection<TMatch> matches = matcher.getAllMatches();
		checker.setMatches(matches);

		engine.addMatchUpdateListener(matcher, new IMatchUpdateListener<TMatch>() {
			@Override
			public void notifyAppearance(final TMatch match) {
				matches.add(match);
			}

			@Override
			public void notifyDisappearance(final TMatch match) {
				matches.remove(match);
			}
		}, false);

		final ResourceSet resourceSet = resource.getResourceSet();
		for (final Resource r : resourceSet.getResources()) {
			for (final EObject eObject : r.getContents()) {
				if (eObject instanceof RailwayContainer) {
					container = (RailwayContainer) eObject;
					break;
				}
			}
		}
	}

	public void waitForSync() throws InterruptedException, ExecutionException, HawkInstanceNotFound, HawkInstanceNotRunning, TException {
		final CompletableFuture<Boolean> syncEnd = new CompletableFuture<Boolean>();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				syncEnd.complete(true);
			}
		};
		hawkResource.addSyncEndListener(runnable);
		client.syncInstance(HAWK_INSTANCE);

		syncEnd.get();
		hawkResource.removeSyncEndListener(runnable);
	}

	@Override
	public void persist() throws IOException {
		try {
			waitForSync();
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void destroy() throws Exception {
		super.destroy();
		resource.unload();
	}

	public String getHawkRepositoryPath() {
		return hawkRepositoryPath;
	}
	
}
