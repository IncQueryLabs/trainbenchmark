/*******************************************************************************
 * Copyright (c) 2010-2015, Benedek Izso, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Benedek Izso - initial API and implementation
 * Gabor Szarnyas - initial API and implementation
 *******************************************************************************/

package hu.bme.mit.trainbenchmark.benchmark.hawk.test;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import hu.bme.mit.trainbenchmark.benchmark.test.BatchTest;

public class HawkBatchTest extends BatchTest {

	@Parameters
	public static Collection<Object[]> data() {
	    return HawkBenchmarkInitializer.getTestParameters();
	}
	
	public HawkBatchTest(final boolean useHawkResource) {
		bi = new HawkBenchmarkInitializer(useHawkResource);
	}

}
