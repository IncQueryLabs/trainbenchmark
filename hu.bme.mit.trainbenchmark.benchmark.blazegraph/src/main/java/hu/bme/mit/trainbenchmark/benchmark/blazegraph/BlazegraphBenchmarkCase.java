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

package hu.bme.mit.trainbenchmark.benchmark.blazegraph;

import java.io.IOException;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.blazegraph.driver.BlazegraphDriver;
import hu.bme.mit.trainbenchmark.benchmark.rdf.RDFBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.sesame.SesameBenchmarkCase;
import hu.bme.mit.trainbenchmark.benchmark.sesame.driver.SesameDriver;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.SesameTransformation;
import hu.bme.mit.trainbenchmark.constants.Query;

public class BlazegraphBenchmarkCase extends SesameBenchmarkCase {

	@Override
	public SesameDriver createDriver(final RDFBenchmarkConfig benchmarkConfig) throws Exception {
		return new BlazegraphDriver(benchmarkConfig);
	}

	@Override
	public Transformation<?, ?> createTransformation(final RDFBenchmarkConfig benchmarkConfig, final SesameDriver driver, final Query query) throws IOException {
		return SesameTransformation.newInstance(driver, query, benchmarkConfig.getScenario());
	}

}
