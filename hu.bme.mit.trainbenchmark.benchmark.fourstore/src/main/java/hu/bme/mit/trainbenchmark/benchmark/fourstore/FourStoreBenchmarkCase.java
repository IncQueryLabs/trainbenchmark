/*******************************************************************************
 * Copyright (c) 2010-2015, Gabor Szarnyas, Benedek Izso, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/
package hu.bme.mit.trainbenchmark.benchmark.fourstore;

import java.io.IOException;
import java.util.Comparator;

import org.openrdf.model.URI;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.AbstractBenchmarkCaseRunner;
import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.benchmarkcases.FourStoreChecker;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.config.FourStoreBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.driver.FourStoreDriver;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.transformations.FourStoreTransformation;
import hu.bme.mit.trainbenchmark.benchmark.sesame.matches.SesameMatch;
import hu.bme.mit.trainbenchmark.benchmark.sesame.matches.SesameMatchComparator;

public class FourStoreBenchmarkCase extends AbstractBenchmarkCaseRunner<SesameMatch, URI, FourStoreDriver, FourStoreBenchmarkConfig> {

	protected FourStoreDriver fourStoreDriver;

	@Override
	public void initialize() throws IOException {
		final FourStoreBenchmarkConfig fsbc = bc;

		driver = new FourStoreDriver(fsbc);
		checker = new FourStoreChecker(fourStoreDriver, fsbc);
	}

	@Override
	protected Comparator<?> getMatchComparator() {
		return new SesameMatchComparator();
	}

	@Override
	protected Transformation<?> getTransformation() throws IOException {
		return FourStoreTransformation.newInstance(fourStoreDriver, bc.getQueries(), bc.getScenario());
	}

}
