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
package hu.bme.mit.trainbenchmark.benchmark.jena.transformations.inject;

import static hu.bme.mit.trainbenchmark.rdf.RDFConstants.BASE_PREFIX;
import static hu.bme.mit.trainbenchmark.rdf.RDFConstants.ID_PREFIX;

import java.util.Collection;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import hu.bme.mit.trainbenchmark.benchmark.jena.driver.JenaDriver;
import hu.bme.mit.trainbenchmark.constants.ModelConstants;

public class JenaTransformationInjectConnectedSegments extends JenaTransformationInject {

	public JenaTransformationInjectConnectedSegments(final JenaDriver driver) {
		super(driver);
	}

	@Override
	public void rhs(final Collection<Resource> segments) throws Exception {
		final Model model = driver.getModel();
		final Property connectsToProperty = model.getProperty(BASE_PREFIX + ModelConstants.CONNECTSTO);
		final Property sensorEdgeProperty = model.getProperty(BASE_PREFIX + ModelConstants.SENSOR_EDGE);
		final Property segmentType = model.getProperty(BASE_PREFIX + ModelConstants.SEGMENT);

		for (final Resource segment1 : segments) {
			// get (segment3) node
			final Selector connectsToEdges0selector = new SimpleSelector(segment1, connectsToProperty, (RDFNode) null);
			final StmtIterator connectsToEdges0 = model.listStatements(connectsToEdges0selector);
			if (!connectsToEdges0.hasNext()) {
				continue;
			}
			final Statement connectsToEdge0 = connectsToEdges0.next();
			final RDFNode segment3 = connectsToEdge0.getObject();

			// get (sensor) node
			final Selector sensorEdgesSelector = new SimpleSelector(segment1, sensorEdgeProperty, (RDFNode) null);
			final StmtIterator sensorEdges = model.listStatements(sensorEdgesSelector);
			if (!sensorEdges.hasNext()) {
				continue;
			}
			final Statement sensorEdge = sensorEdges.next();
			final RDFNode sensor = sensorEdge.getObject();

			// delete (segment1)-[:connectsTo]->(segment3) edge
			model.remove(connectsToEdge0);

			// create (segment2) node
			final Long newVertexId = driver.getNewVertexId();
			final Resource segment2 = model.createResource(BASE_PREFIX + ID_PREFIX + newVertexId);

			model.add(model.createStatement(segment2, RDF.type, segmentType));

			// (segment1)-[:connectsTo]->(segment2)
			model.add(segment1, connectsToProperty, segment2);
			// (segment2)-[:connectsTo]->(segment3)
			model.add(segment2, connectsToProperty, segment3);
			// (segment1)-[:sensor]->(sensor)
			model.add(segment1, sensorEdgeProperty, sensor);
		}
	}

}
