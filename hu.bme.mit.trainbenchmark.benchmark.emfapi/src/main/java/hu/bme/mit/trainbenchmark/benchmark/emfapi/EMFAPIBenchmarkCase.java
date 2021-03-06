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

package hu.bme.mit.trainbenchmark.benchmark.emfapi;

import java.io.IOException;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.emfapi.benchmarkcases.EMFAPIChecker;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.emf.EMFDriver;
import hu.bme.mit.trainbenchmark.emf.benchmarkcases.EMFBenchmarkCase;
import hu.bme.mit.trainbenchmark.emf.matches.EMFMatch;
import hu.bme.mit.trainbenchmark.emf.transformation.EMFTransformation;

public class EMFAPIBenchmarkCase<TBenchmarkConfig extends BenchmarkConfig>
		extends EMFBenchmarkCase<EMFDriver<TBenchmarkConfig>, TBenchmarkConfig, Checker<EMFMatch>> {

	@Override
	public EMFDriver createDriver(final BenchmarkConfig benchmarkConfig) throws Exception {
		return new EMFDriver(benchmarkConfig);
	}

	@Override
	public Checker<EMFMatch> createChecker(final BenchmarkConfig benchmarkConfig, final EMFDriver driver, final Query query)
			throws Exception {
		return (Checker<EMFMatch>) EMFAPIChecker.newInstance(driver, query);
	}

	@Override
	public Transformation<?, ?> createTransformation(final BenchmarkConfig benchmarkConfig, final EMFDriver driver, final Query query)
			throws IOException {
		return EMFTransformation.newInstance(driver, query, benchmarkConfig.getScenario());
	}

}
