package main;

import org.apache.commons.math3.linear.RealVector;

import algorithms.GradientAlgorithm;
import algorithms.MyLBFGS;
import algorithms.Optimizer;
import algorithms.ParticleSwarmOptimalization;
import algorithms.SimulatedAnnealing;
import functions.Function;
import functions.FunctionSPDN;

public class MainSPDN {

	public static void main(String[] args) {
		
		Optimizer opt;
		
		Function f = new FunctionSPDN();
		
		RealVector result;
		
		opt = new GradientAlgorithm();
		result = opt.Method(f);
		System.out.println("Gradient descent: " + result.toString());
		
		
		opt = new ParticleSwarmOptimalization();
		result = opt.Method(f);
		System.out.println("Particle swarm optimalization: " + result.toString());
		

		opt = new SimulatedAnnealing();
		result = opt.Method(f);
		System.out.println("Simulated annealing: " + result.toString());
		
		
		opt = new MyLBFGS();
		result = opt.Method(f);
		System.out.println("L-BFGS: " + result.toString());

	}

}
