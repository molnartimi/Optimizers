package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import org.junit.AfterClass;
import org.junit.Test;

import algorithms.BeesAlgorithm;
import algorithms.GPSOwithMixed;
import algorithms.Optimizer;
import algorithms.ParticleSwarmOptimalization;
import algorithms.SimulatedAnnealing;
import algorithms.MyLBFGS;
import functions.FunctionSPDN;
import hu.bme.mit.inf.petridotnet.spdn.AnalysisConfiguration;
import hu.bme.mit.inf.petridotnet.spdn.Parameter;
import hu.bme.mit.inf.petridotnet.spdn.Reward;
import hu.bme.mit.inf.petridotnet.spdn.Spdn;
import hu.bme.mit.inf.petridotnet.spdn.SpdnAnalyzer;
import other.CsvFileWriter;

public class TestToGetCSVs {
	
	private int m = 3;
	private Optimizer opt;
	private FunctionSPDN f;
	RealVector result;
	
	public TestToGetCSVs() {
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

	@AfterClass
	public static void writeOut(){
		CsvFileWriter.writeOut();
	}
	
	@Test
	public void particleSwarmTest(){
		double fctr = 0;
		double dctr = 0;
		for (int i=0; i<m; i++){
			opt = new ParticleSwarmOptimalization();
			result = opt.Method(f);
			fctr += f.getFctr();
			dctr += f.getDctr();
		}
		System.err.println("PSO: fctr = " + fctr/m + "; dctr = " + dctr/m);
	}
	
	@Test
	public void gpsoWithMixedTest() {
		double fctr = 0;
		double dctr = 0;
		for (int i=0; i<m; i++){
			opt = new GPSOwithMixed();
			result = opt.Method(f);
			fctr += f.getFctr();
			dctr += f.getDctr();
		}
		System.err.println("PSOM: fctr = " + fctr/m + "; dctr = " + dctr/m);
	}
	
	@Test
	public void beesTest(){
		double fctr = 0;
		double dctr = 0;
		for (int i=0; i<m; i++){
			opt = new BeesAlgorithm();
			result = opt.Method(f);
			fctr += f.getFctr();
			dctr += f.getDctr();
		}
		System.err.println("BEES: fctr = " + fctr/m + "; dctr = " + dctr/m);
	}
	
	@Test
	public void simulatedAnnealingTest(){
		double fctr = 0;
		double dctr = 0;
		for (int i=0; i<m; i++){
			opt = new SimulatedAnnealing();
			result = opt.Method(f);
			fctr += f.getFctr();
			dctr += f.getDctr();
		}
		System.err.println("SimAnn: fctr = " + fctr/m + "; dctr = " + dctr/m);
	}
	
	@Test
	public void lbfgsTest(){
		double fctr = 0;
		double dctr = 0;
		for (int i=0; i<m; i++){
			opt = new MyLBFGS();
			result = opt.Method(f);
			fctr += f.getFctr();
			dctr += f.getDctr();
		}
		System.err.println("LBFGS: fctr = " + fctr/m + "; dctr = " + dctr/m);
	}
	
}
