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
package hu.bme.mit.trainbenchmark.benchmark.iqdcore.transformations.repair;

import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ENTRY;

import java.io.IOException;
import java.util.Collection;

import hu.bme.mit.incquerydcore.WildcardInput;
import hu.bme.mit.incquerydcore.WildcardInput.Transaction;
import hu.bme.mit.trainbenchmark.benchmark.iqdcore.match.IQDCoreSemaphoreNeighborMatch;

public class IQDCoreTransformationRepairSemaphoreNeighbor extends IQDCoreTransformationRepair<IQDCoreSemaphoreNeighborMatch> {

	public IQDCoreTransformationRepairSemaphoreNeighbor(final WildcardInput jenaDriver) {
		super(jenaDriver);
	}

	@Override
	public void rhs(final Collection<IQDCoreSemaphoreNeighborMatch> matches) throws IOException {
		final Transaction transaction = input.newTransaction();
		for (final IQDCoreSemaphoreNeighborMatch match : matches) {
			final Long route2 = match.getRoute2();
			final Long semaphore = match.getSemaphore();
			transaction.add(route2, ENTRY, semaphore);
		}
		input.processTransaction(transaction);
	}

}
