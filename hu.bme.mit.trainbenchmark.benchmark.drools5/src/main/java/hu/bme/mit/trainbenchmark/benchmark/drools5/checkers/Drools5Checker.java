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
package hu.bme.mit.trainbenchmark.benchmark.drools5.checkers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.rule.LiveQuery;

import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.drools5.Drools5ResultListener;
import hu.bme.mit.trainbenchmark.benchmark.drools5.driver.Drools5Driver;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.emf.matches.EMFMatch;

public class Drools5Checker extends Checker<EMFMatch> {

	protected final Drools5Driver driver;
	protected Collection<EMFMatch> matches = new HashSet<>();
	protected Drools5ResultListener listener;
	protected LiveQuery liveQuery;
	protected Query query;

	public Drools5Checker(final BenchmarkConfig benchmarkConfig, final Drools5Driver driver, final Query query) throws IOException {
		super();
		this.driver = driver;
		this.query = query;
	
		final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		final String queryFile = benchmarkConfig.getWorkspacePath()
				+ "/hu.bme.mit.trainbenchmark.benchmark.drools5/src/main/resources/queries/" + query + ".drl";
		kbuilder.add(ResourceFactory.newFileResource(queryFile), ResourceType.DRL);

		final KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (final KnowledgeBuilderError error : errors) {
				throw new IOException("Error encountered while reading knowledge base: " + error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		driver.getKbase().addKnowledgePackages(kbuilder.getKnowledgePackages());

	}

	@Override
	public Collection<EMFMatch> check() throws IOException {
		if (liveQuery == null) {
			listener = new Drools5ResultListener(query);
			liveQuery = driver.getKsession().openLiveQuery(query.toString(), new Object[] {}, listener);
		}
		matches = listener.getMatches();
		return matches;
	}

	@Override
	public void destroy() {
		if (liveQuery != null) {
			liveQuery.close();
		}
	}

}
