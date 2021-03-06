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
package hu.bme.mit.trainbenchmark.benchmark.jena.benchmarkcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import hu.bme.mit.trainbenchmark.benchmark.jena.driver.JenaDriver;
import hu.bme.mit.trainbenchmark.benchmark.jena.match.JenaMatch;
import hu.bme.mit.trainbenchmark.benchmark.rdf.RDFBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.rdf.checkers.RDFChecker;
import hu.bme.mit.trainbenchmark.constants.Query;

public class JenaChecker extends RDFChecker<JenaMatch> {

	protected JenaDriver jenaDriver;
	protected org.apache.jena.query.Query jenaQuery;

	public JenaChecker(final JenaDriver jenaDriver, final RDFBenchmarkConfig benchmarkConfig, final Query query) throws IOException {
		super(benchmarkConfig, query);
		this.jenaDriver = jenaDriver;
		this.jenaQuery = QueryFactory.read(queryPath);
	}

	@Override
	public Collection<JenaMatch> check() throws IOException {
		final List<JenaMatch> matches = new ArrayList<>();

		try (QueryExecution queryExecution = QueryExecutionFactory.create(jenaQuery, jenaDriver.getModel())) {
			final ResultSet resultSet = queryExecution.execSelect();

			while (resultSet.hasNext()) {
				final QuerySolution qs = resultSet.next();
				final JenaMatch match = JenaMatch.createMatch(query, qs);
				matches.add(match);
			}
		}

		return matches;
	}

}
