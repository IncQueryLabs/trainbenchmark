package hu.bme.mit.trainbenchmark.benchmark.emfincquery.util;

import com.google.common.collect.Sets;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.InverseDefinedByMatch;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.InverseDefinedByMatcher;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.incquery.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.incquery.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.incquery.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.runtime.matchers.psystem.PBody;
import org.eclipse.incquery.runtime.matchers.psystem.PVariable;
import org.eclipse.incquery.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.incquery.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.incquery.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.incquery.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.incquery.runtime.matchers.psystem.queries.QueryInitializationException;
import org.eclipse.incquery.runtime.matchers.tuple.FlatTuple;

/**
 * A pattern-specific query specification that can instantiate InverseDefinedByMatcher in a type-safe way.
 * 
 * @see InverseDefinedByMatcher
 * @see InverseDefinedByMatch
 * 
 */
@SuppressWarnings("all")
public final class InverseDefinedByQuerySpecification extends BaseGeneratedEMFQuerySpecification<InverseDefinedByMatcher> {
  private InverseDefinedByQuerySpecification() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws IncQueryException if the pattern definition could not be loaded
   * 
   */
  public static InverseDefinedByQuerySpecification instance() throws IncQueryException {
    try{
    	return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
    	throw processInitializerError(err);
    }
  }
  
  @Override
  protected InverseDefinedByMatcher instantiate(final IncQueryEngine engine) throws IncQueryException {
    return InverseDefinedByMatcher.on(engine);
  }
  
  @Override
  public InverseDefinedByMatch newEmptyMatch() {
    return InverseDefinedByMatch.newEmptyMatch();
  }
  
  @Override
  public InverseDefinedByMatch newMatch(final Object... parameters) {
    return InverseDefinedByMatch.newMatch((hu.bme.mit.trainbenchmark.railway.Sensor) parameters[0], (hu.bme.mit.trainbenchmark.railway.Route) parameters[1]);
  }
  
  private static class LazyHolder {
    private final static InverseDefinedByQuerySpecification INSTANCE = make();
    
    public static InverseDefinedByQuerySpecification make() {
      return new InverseDefinedByQuerySpecification();					
    }
  }
  
  private static class GeneratedPQuery extends BaseGeneratedEMFPQuery {
    private final static InverseDefinedByQuerySpecification.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    @Override
    public String getFullyQualifiedName() {
      return "hu.bme.mit.trainbenchmark.benchmark.emfincquery.inverseDefinedBy";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("sensor","route");
    }
    
    @Override
    public List<PParameter> getParameters() {
      return Arrays.asList(new PParameter("sensor", "hu.bme.mit.trainbenchmark.railway.Sensor"),new PParameter("route", "hu.bme.mit.trainbenchmark.railway.Route"));
    }
    
    @Override
    public Set<PBody> doGetContainedBodies() throws QueryInitializationException {
      Set<PBody> bodies = Sets.newLinkedHashSet();
      try {
      	{
      		PBody body = new PBody(this);
      		PVariable var_sensor = body.getOrCreateVariableByName("sensor");
      		PVariable var_route = body.getOrCreateVariableByName("route");
      		PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
      		body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
      			new ExportedParameter(body, var_sensor, "sensor"),
      			
      			new ExportedParameter(body, var_route, "route")
      		));
      		new TypeConstraint(body, new FlatTuple(var_route), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("http://www.semanticweb.org/ontologies/2015/trainbenchmark", "Route")));
      		new TypeConstraint(body, new FlatTuple(var_route, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("http://www.semanticweb.org/ontologies/2015/trainbenchmark", "Route", "definedBy")));
      		new Equality(body, var__virtual_0_, var_sensor);
      		bodies.add(body);
      	}
      	// to silence compiler error
      	if (false) throw new IncQueryException("Never", "happens");
      } catch (IncQueryException ex) {
      	throw processDependencyException(ex);
      }
      return bodies;
    }
  }
}
