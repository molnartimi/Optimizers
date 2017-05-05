package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import algorithms.*;
import functions.FunctionSPDN;
import hu.bme.mit.inf.petridotnet.spdn.AnalysisConfiguration;
import hu.bme.mit.inf.petridotnet.spdn.Parameter;
import hu.bme.mit.inf.petridotnet.spdn.Reward;
import hu.bme.mit.inf.petridotnet.spdn.Spdn;
import hu.bme.mit.inf.petridotnet.spdn.SpdnAnalyzer;

public class AlgorithmTest {
	public static double epszilon=0.001;
	private Optimizer opt;
	private FunctionSPDN f;
	RealVector result;
	
	public AlgorithmTest(){
		f = setModel(0.727272727272727, 1.09090909090909);
		result = MatrixUtils.createRealVector(new double[f.getDimension()]);
	}
	
	private FunctionSPDN setModel(double idleEmpirical, double servedRequestsEmpirical) {
		Spdn spdn = new Spdn("E:/Timi/BME/6.félév/Önálló laboratórium/Optimizers");
    
		SpdnAnalyzer analyzer = spdn.openModel("src/models/simple-server.pnml", AnalysisConfiguration.DEFAULT);

		// parameters
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(Parameter.ofName("requestRate"));
		parameters.add(Parameter.ofName("serviceTime"));

		// rewards
		Reward idle = Reward.instantaneous("Idle");
		Reward servedRequests = Reward.instantaneous("ServedRequests");
		ArrayList<Reward> rewardList = new ArrayList<Reward>();
		rewardList.add(idle);
		rewardList.add(servedRequests);

		Map<Reward, Double> empiricalMeasurements = new HashMap<Reward, Double>();
		empiricalMeasurements.put(idle, idleEmpirical);
		empiricalMeasurements.put(servedRequests, servedRequestsEmpirical);

		return new FunctionSPDN(analyzer, parameters, rewardList, empiricalMeasurements);
	}
	
	
	private RealVector convertResult(RealVector v){ 
	    RealVector result = MatrixUtils.createRealVector(new double[f.getDimension()]); 
	    for(int i=0; i<f.getDimension(); i++) 
	      result.setEntry(i, Math.exp(v.getEntry(i))); 
	    return result; 
	  } 

	
	@Test
	//@Ignore
	public void gradientTest(){
		opt = new GradientAlgorithm();
		
		result = opt.Method(f);
		System.out.println("Gradient descent:");
	}
	
	//@Ignore
	@Test
	public void particleSwarmTest(){
		opt = new ParticleSwarmOptimalization();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization:");
	}
	
	//@Ignore
	@Test
	public void lbfgsTest(){
		opt = new MyLBFGS(100);
		
		result = opt.Method(f);
		System.out.println("L-BFGS:");
	}
	
	//@Ignore
	@Test
	public void gpsoWithGVTest(){
		opt = new GPSOwithGradientValue();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient value:");
	}
	
	//@Ignore
	@Test
	public void gpsoWithGDTest() {
		opt = new GPSOwithGradientDescent();

		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient descent:");
	}
	
	//@Ignore
	@Test
	public void gpsoWithMixedTest() {
		opt = new GPSOwithMixed();

		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient descent:");
	}
	
	//@Ignore
	@Test
	public void simulatedAnnealingTest(){
		opt = new SimulatedAnnealing();
		
		result = opt.Method(f);
		System.out.println("Simulated annealing:");
	}
	
	@Test
	//@Ignore
	public void beesTest(){
		opt = new BeesAlgorithm();
		
		result = opt.Method(f);
		System.out.println("Bees algorithm:");
	}
	
	@After
	public void writeOutResult(){
		System.out.println("- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("- Computing function value: " + f.getFctr());
		System.out.println("- Computing gradient value: " + f.getDctr());
		System.out.println();
		f.restartCtrs();
	}
}
