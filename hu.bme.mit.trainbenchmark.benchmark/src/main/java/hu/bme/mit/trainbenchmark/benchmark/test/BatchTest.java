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

import static hu.bme.mit.trainbenchmark.constants.Query.CONNECTEDSEGMENTS;
import static hu.bme.mit.trainbenchmark.constants.Query.POSLENGTH;
import static hu.bme.mit.trainbenchmark.constants.Query.ROUTESENSOR;
import static hu.bme.mit.trainbenchmark.constants.Query.SEMAPHORENEIGHBOR;
import static hu.bme.mit.trainbenchmark.constants.Query.SWITCHSENSOR;
import static hu.bme.mit.trainbenchmark.constants.Query.SWITCHSET;
import static hu.bme.mit.trainbenchmark.constants.Scenario.BATCH;

import org.junit.Test;

public abstract class BatchTest extends TrainBenchmarkTest {
	
	@Test
	public void connectedSegments() throws Exception {
		testQuery(CONNECTEDSEGMENTS, BATCH, 0);
	}

	@Test
	public void posLength() throws Exception {
		testQuery(POSLENGTH, BATCH, 0);
	}

	@Test
	public void routeSensor() throws Exception {
		testQuery(ROUTESENSOR, BATCH, 0);
	}

	@Test
	public void semaphoreNeighbor() throws Exception {
		testQuery(SEMAPHORENEIGHBOR, BATCH, 0);
	}

	@Test
	public void switchSensor() throws Exception {
		testQuery(SWITCHSENSOR, BATCH, 0);
	}

	@Test
	public void switchSet() throws Exception {
		testQuery(SWITCHSET, BATCH, 0);
	}

}