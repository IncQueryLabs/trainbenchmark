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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import hu.bme.mit.trainbenchmark.emf.EMFDriver;
import hu.bme.mit.trainbenchmark.emf.matches.EMFRouteSensorMatch;
import hu.bme.mit.trainbenchmark.railway.RailwayPackage;
import hu.bme.mit.trainbenchmark.railway.Route;
import hu.bme.mit.trainbenchmark.railway.Sensor;
import hu.bme.mit.trainbenchmark.railway.Switch;
import hu.bme.mit.trainbenchmark.railway.SwitchPosition;

public class EMFAPIRouteSensorChecker extends EMFAPIChecker<EMFRouteSensorMatch> {

	public EMFAPIRouteSensorChecker(final EMFDriver emfDriver) {
		super(emfDriver);
	}

	@Override
	public Collection<EMFRouteSensorMatch> check() {
		matches = new ArrayList<>();
		final TreeIterator<EObject> contents = emfDriver.getContainer().eAllContents();
		while (contents.hasNext()) {
			final EObject eObject = contents.next();

			// (route:Route)
			if (RailwayPackage.eINSTANCE.getRoute().isInstance(eObject)) {
				final Route route = (Route) eObject;
				// (route)-[:follows]->(swP:SwitchPosition)
				for (final SwitchPosition swP : route.getFollows()) {
					// (swP:switchPosition)-[:switch]->(sw:Switch)
					final Switch sw = swP.getSwitch();
					if (sw == null) {
						continue;
					}
					
					// (switch:Switch)-[:sensor]->(sensor:Sensor)
					final Sensor sensor = sw.getSensor();
					if (sensor == null) {
						continue;
					}

					// (route)-[:definedBy]->(sensor) NAC
					if (!route.getDefinedBy().contains(sensor)) {
						final EMFRouteSensorMatch match = new EMFRouteSensorMatch(route, sensor, swP, sw);
						matches.add(match);
					}
				}
			}
		}

		return matches;
	}

}
