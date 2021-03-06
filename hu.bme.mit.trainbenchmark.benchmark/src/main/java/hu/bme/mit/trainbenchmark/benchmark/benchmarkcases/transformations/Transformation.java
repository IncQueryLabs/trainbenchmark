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
package hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations;

import java.util.Collection;

import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;

public abstract class Transformation<TObject, TDriver extends Driver> {

	protected TDriver driver;
	
	public Transformation(final TDriver driver) {
		this.driver = driver;
	}

	// As the transformations are implemented on a wide range of technologies, they may throw any exception.
	// Using "throws Exception" is generally considered bad practice in production systems, however, it is acceptible in the benchmark code.
	public abstract void rhs(Collection<TObject> objects) throws Exception;

}
