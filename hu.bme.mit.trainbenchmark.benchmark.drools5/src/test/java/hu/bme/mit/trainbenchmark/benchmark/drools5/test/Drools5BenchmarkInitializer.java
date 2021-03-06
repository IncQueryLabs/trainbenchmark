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

package hu.bme.mit.trainbenchmark.benchmark.drools5.test;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.drools5.Drools5BenchmarkCase;
import hu.bme.mit.trainbenchmark.benchmark.scenarios.BenchmarkRunner;
import hu.bme.mit.trainbenchmark.benchmark.test.TestBenchmarkInitializer;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.constants.Scenario;

public class Drools5BenchmarkInitializer extends TestBenchmarkInitializer {

	@Override
	protected BenchmarkRunner initializeBenchmark(final Query query, final Scenario scenario) {
		final BenchmarkConfig benchmarkConfig = new BenchmarkConfig("Drools5", scenario, size, runIndex, query, iterationCount, transformationStrategy,
				transformationConstant);
		return new BenchmarkRunner(benchmarkConfig, new Drools5BenchmarkCase());
	}

}
