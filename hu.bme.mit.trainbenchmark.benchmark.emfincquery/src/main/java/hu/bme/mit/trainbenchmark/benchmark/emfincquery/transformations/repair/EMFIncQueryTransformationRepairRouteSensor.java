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
package hu.bme.mit.trainbenchmark.benchmark.emfincquery.transformations.repair;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.incquery.runtime.api.impl.BasePatternMatch;

import hu.bme.mit.trainbenchmark.benchmark.emfincquery.RouteSensorMatch;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.config.EMFIncQueryBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.driver.EMFIncQueryBaseDriver;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.transformations.EMFIncQueryTransformation;

public class EMFIncQueryTransformationRepairRouteSensor extends EMFIncQueryTransformation<RouteSensorMatch> {

	public EMFIncQueryTransformationRepairRouteSensor(final EMFIncQueryBaseDriver<? extends BasePatternMatch, EMFIncQueryBenchmarkConfig> driver) {
		super(driver);
	}

	@Override
	public void rhs(final Collection<RouteSensorMatch> matches) throws IOException {
		for (final RouteSensorMatch match : matches) {
			match.getRoute().getDefinedBy().add(match.getSensor());
		}
	}

}
