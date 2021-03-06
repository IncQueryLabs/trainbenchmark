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

package hu.bme.mit.trainbenchmark.benchmark.test;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.ImmutableList;

import eu.mondo.sam.core.results.BenchmarkResult;
import eu.mondo.sam.core.results.JsonSerializer;
import eu.mondo.sam.core.results.MetricResult;
import eu.mondo.sam.core.results.PhaseResult;
import hu.bme.mit.trainbenchmark.benchmark.scenarios.BenchmarkRunner;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.constants.Scenario;

@RunWith(Parameterized.class)
public abstract class TrainBenchmarkTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { {} });
	}

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	protected TestBenchmarkInitializer bi;

	protected void testQuery(final Query query, final Scenario scenario, final int expectedResultSize)
			throws Exception {
		final BenchmarkRunner bl = bi.initializeBenchmark(query, scenario);
		runQuery(bl, ImmutableList.of(expectedResultSize));
	}

	protected void testTransformation(final Query query, final Scenario scenario, final int expectedResultSize1,
			final int expectedResultSize2) throws Exception {
		final BenchmarkRunner bl = bi.initializeBenchmark(query, scenario);
		runQuery(bl, ImmutableList.of(expectedResultSize1, expectedResultSize2));
	}

	private void runQuery(final BenchmarkRunner benchmarkRunner, final List<Integer> expectedResultSizes)
			throws Exception {
		JsonSerializer.setResultPath("../results/test/");
		final BenchmarkResult benchmarkResult = benchmarkRunner.runBenchmark();

		final List<Integer> resultSizes = new ArrayList<>();
		for (final PhaseResult pr : benchmarkResult.getPhaseResults()) {
			final String name = pr.getPhaseName();
			if ("Check".equals(name) || "Recheck".equals(name)) {
				for (final MetricResult m : pr.getMetrics()) {
					if ("Matches".equals(m.getName())) {
						final Integer resultSize = Integer.parseInt(m.getValue());
						resultSizes.add(resultSize);
					}
				}
			}
		}
		for (int i = 0; i < expectedResultSizes.size(); i++) {
			collector.checkThat(resultSizes.get(i), equalTo(expectedResultSizes.get(i)));
		}
	}
}
