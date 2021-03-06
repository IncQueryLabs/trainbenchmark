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
package hu.bme.mit.trainbenchmark.benchmark.emfincquery;

import java.io.IOException;
import java.util.Comparator;

import org.eclipse.incquery.runtime.api.impl.BasePatternMatch;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.AbstractBenchmarkCase;
import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.checker.EMFIncQueryChecker;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.config.EMFIncQueryBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.driver.EMFIncQueryDriver;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.matches.EMFIncQueryMatchComparator;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.transformations.EMFIncQueryTransformation;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.railway.RailwayElement;

public class EMFIncQueryBenchmarkCase<TMatch extends BasePatternMatch> extends
		AbstractBenchmarkCase<TMatch, RailwayElement, EMFIncQueryDriver<TMatch>, EMFIncQueryBenchmarkConfig, EMFIncQueryChecker<TMatch>> {

	@Override
	public EMFIncQueryDriver<TMatch> createDriver(final EMFIncQueryBenchmarkConfig benchmarkConfig) throws Exception {
		return new EMFIncQueryDriver(benchmarkConfig);
	}

	@Override
	public EMFIncQueryChecker<TMatch> createChecker(final EMFIncQueryBenchmarkConfig benchmarkConfig,
			final EMFIncQueryDriver<TMatch> driver, final Query query) throws Exception {
		return (EMFIncQueryChecker<TMatch>) EMFIncQueryChecker.newInstance(benchmarkConfig, driver, query);
	}

	@Override
	public Transformation<?, ?> createTransformation(final EMFIncQueryBenchmarkConfig benchmarkConfig,
			final EMFIncQueryDriver<TMatch> driver, final Query query) throws IOException {
		return EMFIncQueryTransformation.newInstance(driver, query, benchmarkConfig.getScenario());
	}

	@Override
	public Comparator<?> createMatchComparator() {
		return new EMFIncQueryMatchComparator();
	}

}
