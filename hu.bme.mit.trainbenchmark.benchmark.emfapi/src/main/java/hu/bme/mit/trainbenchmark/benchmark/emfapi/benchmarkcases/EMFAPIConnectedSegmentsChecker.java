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
import hu.bme.mit.trainbenchmark.emf.matches.EMFConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.railway.RailwayPackage;
import hu.bme.mit.trainbenchmark.railway.Segment;
import hu.bme.mit.trainbenchmark.railway.Sensor;
import hu.bme.mit.trainbenchmark.railway.TrackElement;

public class EMFAPIConnectedSegmentsChecker extends EMFAPIChecker<EMFConnectedSegmentsMatch> {

	public EMFAPIConnectedSegmentsChecker(final EMFDriver<?> emfDriver) {
		super(emfDriver);
	}

	@Override
	public Collection<EMFConnectedSegmentsMatch> check() {
		matches = new ArrayList<>();

		final TreeIterator<EObject> contents = emfDriver.getContainer().eAllContents();
		while (contents.hasNext()) {
			final EObject eObject = contents.next();

			// (sensor:Sensor)
			if (!RailwayPackage.eINSTANCE.getSensor().isInstance(eObject)) {
				continue;
			}
			final Sensor sensor = (Sensor) eObject;

			// (sensor)-[:element]->(segment1:Segment)
			for (final TrackElement element1 : sensor.getElements()) {
				if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element1)) {
					continue;
				}
				final Segment segment1 = (Segment) element1;

				// (segment1)-[:connectsTo]->(segment2:Segment)
				for (final TrackElement element2 : segment1.getConnectsTo()) {
					if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element2)) {
						continue;
					}
					final Segment segment2 = (Segment) element2;

					// (segment2)-[:connectsTo]->(segment3:Segment)
					for (final TrackElement element3 : segment2.getConnectsTo()) {
						if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element3)) {
							continue;
						}
						final Segment segment3 = (Segment) element3;

						// (segment3)-[:connectsTo]->(segment4:Segment)
						for (final TrackElement element4 : segment3.getConnectsTo()) {
							if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element4)) {
								continue;
							}
							final Segment segment4 = (Segment) element4;

							// (segment4)-[:connectsTo]->(segment5:Segment)
							for (final TrackElement element5 : segment4.getConnectsTo()) {
								if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element5)) {
									continue;
								}
								final Segment segment5 = (Segment) element5;

								// (segment5)-[:connectsTo]->(segment6:Segment)
								for (final TrackElement element6 : segment5.getConnectsTo()) {
									if (!RailwayPackage.eINSTANCE.getSegment().isInstance(element6)) {
										continue;
									}
									final Segment segment6 = (Segment) element6;

									// (segment6:Segment)-[:sensor]->(sensor)
									if (segment6.getSensor() != sensor) {
										continue;
									}

									final EMFConnectedSegmentsMatch csm = new EMFConnectedSegmentsMatch(sensor, segment1, segment2,
											segment3, segment4, segment5, segment6);
									matches.add(csm);
								}
							}
						}
					}
				}
			}
		}

		return matches;
	}

}
