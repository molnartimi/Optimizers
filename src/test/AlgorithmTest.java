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
//		f = setModel("E:/Timi/BME/6.félév/Önálló laboratórium/Optimizers",
//				"src/models/simple-server.pnml",
//				new String[]{"requestRate", "serviceTime"},
//				new String[]{"Idle", "ServedRequests"},
//				new double[]{0.727272727272727, 1.09090909090909});
		
		f = setModel("E:/Timi/BME/6.félév/Önálló laboratórium/Optimizers",
				"src/models/vcl_stochastic.pnml",
				new String[]{"incomingRate", "dispatchTime","warmDispatchTime","jobTime","powerTime","powerUsage","idlePowerFactor"},
				new String[]{"jobsFinished", "powerUsage","noFreeMachines","jobsDispatched","machinesWorking","hotMachinesWorking","coldStarted"},
				new double[]{0.0148748002933323,5.20955485293811,0.00209451703730028,0.0148748002307829,2.3647315051718,2.17735632582612,6.36702992684728E-7});
		result = MatrixUtils.createRealVector(new double[f.getDimension()]);
	}
	
	public static FunctionSPDN setModel(String project, String model, String[] parameterArray, String[] rewardArray, double[] empiricalArray) {
		Spdn spdn = new Spdn(project);
    
		SpdnAnalyzer analyzer = spdn.openModel(model, AnalysisConfiguration.DEFAULT);

		// parameters
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(String s : parameterArray){
			parameters.add(Parameter.ofName(s));
		}
		
		// rewards
		ArrayList<Reward> rewardList = new ArrayList<Reward>();
		for(String s : rewardArray){
			rewardList.add(Reward.instantaneous(s));
		}

		Map<Reward, Double> empiricalMeasurements = new HashMap<Reward, Double>();
		for(int i = 0; i<rewardList.size(); i++){
			empiricalMeasurements.put(rewardList.get(i), empiricalArray[i]);
		}

		return new FunctionSPDN(analyzer, parameters, rewardList, empiricalMeasurements);
	}	
	
	
	private RealVector convertResult(RealVector v){ 
	    RealVector result = MatrixUtils.createRealVector(new double[f.getDimension()]); 
	    for(int i=0; i<f.getDimension(); i++) 
	      result.setEntry(i, Math.exp(v.getEntry(i))); 
	    return result; 
	  } 

	
	@Test
	@Ignore
	public void gradientTest(){
		opt = new GradientAlgorithm();
		
		result = opt.Method(f);
		System.out.println("Gradient descent:");
	}
	
	@Ignore
	@Test
	public void particleSwarmTest(){
		opt = new ParticleSwarmOptimalization();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization:");
	}
	
	@Ignore
	@Test
	public void lbfgsTest(){
		opt = new MyLBFGS(100);
		
		result = opt.Method(f);
		System.out.println("L-BFGS:");
	}
	
	@Ignore
	@Test
	public void gpsoWithGVTest(){
		opt = new GPSOwithGradientValue();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient value:");
	}
	
	@Ignore
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
	
	@Ignore
	@Test
	public void simulatedAnnealingTest(){
		opt = new SimulatedAnnealing();
		
		result = opt.Method(f);
		System.out.println("Simulated annealing:");
	}
	
	@Test
	@Ignore
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
