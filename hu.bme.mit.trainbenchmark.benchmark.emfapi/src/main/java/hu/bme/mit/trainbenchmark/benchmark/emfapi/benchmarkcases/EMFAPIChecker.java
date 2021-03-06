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
package hu.bme.mit.trainbenchmark.benchmark.emfapi.benchmarkcases;

import java.util.Collection;

import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.emf.EMFDriver;
import hu.bme.mit.trainbenchmark.emf.matches.EMFMatch;

public abstract class EMFAPIChecker<TMatch extends EMFMatch> extends Checker<TMatch> {

	protected Collection<TMatch> matches;
	protected final EMFDriver<?> emfDriver;

	public EMFAPIChecker(final EMFDriver<?> emfDriver) {
		this.emfDriver = emfDriver;
	}

	public static EMFAPIChecker<?> newInstance(final EMFDriver<?> driver, final Query query) {
		switch (query) {
		case CONNECTEDSEGMENTS:
			return new EMFAPIConnectedSegmentsChecker(driver);
		case POSLENGTH:
			return new EMFAPIPosLengthChecker(driver);
		case ROUTESENSOR:
			return new EMFAPIRouteSensorChecker(driver);
		case SEMAPHORENEIGHBOR:
			return new EMFAPISemaphoreNeighborChecker(driver);
		case SWITCHSENSOR:
			return new EMFAPISwitchSensorChecker(driver);
		case SWITCHSET:
			return new EMFAPISwitchSetChecker(driver);
		default:
			throw new UnsupportedOperationException("Query " + query + " not supported");
		}
	}

}
