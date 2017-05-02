package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import algorithms.GradientAlgorithm;
import algorithms.MyLBFGS;
import algorithms.Optimizer;
import algorithms.ParticleSwarmOptimalization;
import algorithms.SimulatedAnnealing;
import functions.Function;
import functions.FunctionSPDN;
import hu.bme.mit.inf.petridotnet.spdn.AnalysisConfiguration;
import hu.bme.mit.inf.petridotnet.spdn.Parameter;
import hu.bme.mit.inf.petridotnet.spdn.Reward;
import hu.bme.mit.inf.petridotnet.spdn.Spdn;
import hu.bme.mit.inf.petridotnet.spdn.SpdnAnalyzer;

public class MainSPDN {

	public static Function setModel(double idleEmpirical, double servedRequestsEmpirical) {
		Spdn spdn = new Spdn("E:/Timi/BME/6.f�l�v/�n�ll� laborat�rium/Optimizers");
    
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

	public static void main(String[] args) {

		long startTime = System.nanoTime();

		Optimizer opt;

		RealVector result;

		Function f = setModel(0.4, 1.2);

		opt = new GradientAlgorithm();
		result = opt.Method(f);
		System.out.println("Gradient descent: " + result.toString());
		System.out.println("FunctionSPDN.runAnalyzer() counter: " + FunctionSPDN.ctr);
		FunctionSPDN.ctr = 0;

		/*
		 * opt = new ParticleSwarmOptimalization(); result = opt.Method(f);
		 * System.out.println("Particle swarm optimalization: " +
		 * result.toString());
		 * System.out.println("FunctionSPDN.runAnalyzer() counter: " +
		 * FunctionSPDN.ctr); FunctionSPDN.ctr=0;
		 * 
		 * opt = new PSOwithGD(); result = opt.Method(f); System.out.
		 * println("Particle swarm optimalization with Gradient descent: " +
		 * result.toString());
		 * System.out.println("FunctionSPDN.runAnalyzer() counter: " +
		 * FunctionSPDN.ctr); FunctionSPDN.ctr=0;
		 * 
		 * opt = new SimulatedAnnealing(); result = opt.Method(f);
		 * System.out.println("Simulated annealing: " + result.toString());
		 * System.out.println("FunctionSPDN.runAnalyzer() counter: " +
		 * FunctionSPDN.ctr); FunctionSPDN.ctr=0;
		 * 
		 * opt = new MyLBFGS(); result = opt.Method(f);
		 * System.out.println("L-BFGS: " + result.toString());
		 * System.out.println("FunctionSPDN.runAnalyzer() counter: " +
		 * FunctionSPDN.ctr); FunctionSPDN.ctr=0;
		 */
	}

}
