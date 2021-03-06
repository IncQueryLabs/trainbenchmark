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

package hu.bme.mit.trainbenchmark.benchmark.neo4j;

import hu.bme.mit.trainbenchmark.benchmark.neo4j.config.Neo4jBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.scenarios.BenchmarkRunner;

public class Neo4jBenchmarkMain {

	public static void main(final String[] args) throws Exception {
		final Neo4jBenchmarkConfig benchmarkConfig = new Neo4jBenchmarkConfig(args);
		final BenchmarkRunner benchmarkRunner = new BenchmarkRunner(benchmarkConfig, new Neo4jBenchmarkCase());
		benchmarkRunner.runBenchmark();
	}

}
