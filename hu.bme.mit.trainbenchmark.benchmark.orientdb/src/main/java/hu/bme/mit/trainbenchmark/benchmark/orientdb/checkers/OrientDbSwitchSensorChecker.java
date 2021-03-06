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
package hu.bme.mit.trainbenchmark.benchmark.orientdb.checkers;

import hu.bme.mit.trainbenchmark.benchmark.orientdb.driver.OrientDbDriver;
import hu.bme.mit.trainbenchmark.benchmark.orientdb.matches.OrientDbSwitchSensorMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.tinkerpop.pipes.util.structures.Row;

public class OrientDbSwitchSensorChecker extends OrientDbChecker<OrientDbSwitchSensorMatch> {

	public OrientDbSwitchSensorChecker(final OrientDbDriver driver) {
		super(driver);
	}

	@Override
	public Collection<OrientDbSwitchSensorMatch> check() throws IOException {
		
		final Collection<OrientDbSwitchSensorMatch> matches = new ArrayList<OrientDbSwitchSensorMatch>();
		List<String> lines = FileUtils.readLines(FileUtils.getFile(queryPath + "SwitchSensor.gremlin"));
		List<Row> result = driver.runQuery(lines);
		
		for (Row row : result) {
			matches.add(new OrientDbSwitchSensorMatch(row));
		}
		
		return matches;
	}

}
