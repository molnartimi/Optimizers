package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
	
	public static Function setModel(){
		Spdn spdn = new Spdn("F:/Timi/BME/6.félév/Önálló laboratórium/Optimizers");
        InputStream model;
		try {
			model = new FileInputStream("src/models/simple-server.pnml");
			SpdnAnalyzer analyzer = spdn.openModel(model, AnalysisConfiguration.DEFAULT);
			List<Parameter> parameters = new ArrayList<Parameter>();
	        parameters.add(Parameter.ofName("requestRate"));
	        parameters.add(Parameter.ofName("serviceTime"));
	        Reward idle = Reward.instantaneous("Idle");
	        Reward servedRequests = Reward.instantaneous("ServedRequests");
	        Map<Reward, Double> empiricalMeasurements = new HashMap<Reward,Double>();
	        empiricalMeasurements.put(idle, 0.4);
	        empiricalMeasurements.put(servedRequests, 0.02);
	        
	        return new FunctionSPDN(analyzer, parameters, empiricalMeasurements);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		
		Optimizer opt;
		
		RealVector result;
		
		Function f = setModel();
		
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
