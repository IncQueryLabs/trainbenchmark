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

package hu.bme.mit.trainbenchmark.benchmark.sesame.test;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import hu.bme.mit.trainbenchmark.benchmark.test.MinimalTest;

public class SesameMinimalTest extends MinimalTest {

	@Parameters
	public static Collection<Object[]> data() {
	    return SesameBenchmarkInitializer.getTestParameters();
	}
	
	public SesameMinimalTest(final boolean inferencing) {
		bi = new SesameBenchmarkInitializer(inferencing);
	}

}
