package test;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Ignore;
import org.junit.Test;

import algorithms.*;
import functions.Function;
import functions.FunctionSPDN;
import main.MainSPDN;

public class AlgorithmTest {
	private Optimizer opt;
	private Function f = MainSPDN.setModel(0.727272727272727, 1.09090909090909);
	RealVector result;
	
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
		System.out.println("Gradient descent: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr=0;
	}
	
	//@Ignore
	@Test
	public void particleSwarmTest(){
		opt = new ParticleSwarmOptimalization();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr=0;
	}
	
	//@Ignore
	@Test
	public void lbfgsTest(){
		opt = new MyLBFGS(100);
		
		result = opt.Method(f);
		System.out.println("L-BFGS: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr=0;
	}
	
	@Ignore
	@Test
	public void gpsoWithGVTest(){
		opt = new GPSOwithGradientValue();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient value: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr=0;
	}
	
	@Ignore
	@Test
	public void gpsoWithGDTest() {
		opt = new GPSOwithGradientDescent();

		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient descent: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr = 0;
	}
	
	//@Ignore
	@Test
	public void gpsoWithMixedTest() {
		opt = new GPSOwithMixed();

		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient descent: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr = 0;
	}
	
	//@Ignore
	@Test
	public void simulatedAnnealingTest(){
		opt = new SimulatedAnnealing();
		
		result = opt.Method(f);
		System.out.println("Simulated annealing: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr=0;
	}
	
	@Test
	//@Ignore
	public void beesTest(){
		opt = new BeesAlgorithm();
		
		result = opt.Method(f);
		System.out.println("Bees algorithm: \n- Point:" + convertResult(result).toString() + "\n- Value: " + f.f(result));
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		System.out.println();
		FunctionSPDN.ctr=0;
	}
}
