package algorithms;

import org.apache.commons.math3.linear.RealVector;
import org.junit.Ignore;
import org.junit.Test;

import functions.Function;
import functions.FunctionSPDN;
import main.MainSPDN;

public class AlgorithmTest {
	private Optimizer opt;
	private Function f = MainSPDN.setModel(0.4, 1);
	RealVector result;
	
	
	@Test
	@Ignore
	public void gradientTest(){
		opt = new GradientAlgorithm();
		
		result = opt.Method(f);
		System.out.println("Gradient descent: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr=0;
	}
	
	//@Ignore
	@Test
	public void particleSwarmTest(){
		opt = new ParticleSwarmOptimalization();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr=0;
	}
	
	@Ignore
	@Test
	public void lbfgsTest(){
		opt = new MyLBFGS(100);
		
		result = opt.Method(f);
		System.out.println("L-BFGS: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr=0;
	}
	
	//@Ignore
	@Test
	public void gpsoWithGVTest(){
		opt = new GPSOwithGradientValue();
		
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient value: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr=0;
	}
	
	//@Ignore
	@Test
	public void gpsoWithGDTest() {
		opt = new GPSOwithGradientDescent();

		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient descent: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr = 0;
	}
	
	//@Ignore
	@Test
	public void gpsoWithMixedTest() {
		opt = new GPSOwithMixed();

		result = opt.Method(f);
		System.out.println("Particle swarm optimalization with gradient descent: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr = 0;
	}
	
	@Ignore
	@Test
	public void simulatedAnnealingTest(){
		opt = new SimulatedAnnealing();
		
		result = opt.Method(f);
		System.out.println("Simulated annealing: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr=0;
	}
	
	@Test
	@Ignore
	public void beesTest(){
		opt = new BeesAlgorithm();
		
		result = opt.Method(f);
		System.out.println("Bees algorithm: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr=0;
	}
}
