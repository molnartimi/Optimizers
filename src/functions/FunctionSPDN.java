package functions;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hu.bme.mit.inf.petridotnet.spdn.AnalysisResult;
import hu.bme.mit.inf.petridotnet.spdn.Parameter;
import hu.bme.mit.inf.petridotnet.spdn.Reward;
import hu.bme.mit.inf.petridotnet.spdn.SpdnAnalyzer;
import main.Main;

public class FunctionSPDN implements Function {
	private SpdnAnalyzer analyzer;
	private List<Parameter> parameters;
	private List<Reward> rewards;
	private Map<Reward,Double> empiricalMeasurements;
	public static int ctr = 0;
	
	/**
	 * 
	 * @param analyzer
	 *            Analyzer with open model.
	 * @param parameters
	 *            Parameters of the model.
	 * @param empiricalMeasurements
	 *            Rewards and their measured values, we want to invert the
	 *            measurement functions to obtain parameter values.
	 */
	public FunctionSPDN(SpdnAnalyzer analyzer, List<Parameter> parameters, List<Reward> rewards, Map<Reward, Double> empiricalMeasurements) {
		this.analyzer = analyzer;
		this.parameters = parameters;
		this.rewards = rewards;
		this.empiricalMeasurements = empiricalMeasurements;
		
	}
	
	private AnalysisResult runAnalyzer(RealVector variables){
		ctr++;
		
		Reward reward1 = rewards.get(0);
        Reward reward2 = rewards.get(1);
        
        AnalysisResult result = analyzer.createAnalysisBuilder()
                    .withParameter(parameters.get(0), Math.exp(variables.getEntry(0)))
                    .withParameter(parameters.get(1), Math.exp(variables.getEntry(1)))
                    .withReward(reward1,parameters.get(0), parameters.get(1))
                    .withReward(reward2,parameters.get(0), parameters.get(1))
                    .run();
        
        return result;
	}
		
	
	@Override
	public double f(RealVector variables) {
        double fResult = 0;
        
        Reward reward1 = rewards.get(0);
        Reward reward2 = rewards.get(1);
        
        AnalysisResult result = runAnalyzer(variables);
            
        double idleResult = result.getValue(reward1);
        double servedRequestsResult = result.getValue(reward2);
            
        fResult = 	Math.pow(empiricalMeasurements.get(reward1)-idleResult, 2) + 
        			Math.pow(empiricalMeasurements.get(reward2)-servedRequestsResult, 2);
            		
        return fResult;
	}

	@Override
	public double df(double x) {
		System.err.println("FunctionSPDN.df(): It's not implemented yet. :(");
		return 0;
	}

	@Override
	public RealVector Df(RealVector variables) {
        double[] fDResult = new double[getDimension()];
        
        Reward reward1 = rewards.get(0);
        Reward reward2 = rewards.get(1);
        
        AnalysisResult result = runAnalyzer(variables);
        
        double idleResult = result.getValue(reward1);
        double servedRequestsResult = result.getValue(reward2);
        
        double idleWithRequestRate = result.getSensitivity(reward1, parameters.get(0));
        double idleWithServiceTime = result.getSensitivity(reward1, parameters.get(1));
        double servedRequestsWithRequestRate = result.getSensitivity(reward2, parameters.get(0));
        double servedRequestsWithServiceTime = result.getSensitivity(reward2, parameters.get(1));
        
        fDResult[0] = -2*(empiricalMeasurements.get(reward1)-idleResult) * idleWithRequestRate * Math.exp(variables.getEntry(0)) +
        			  -2*(empiricalMeasurements.get(reward2)-servedRequestsResult) * servedRequestsWithRequestRate * Math.exp(variables.getEntry(0));
        fDResult[1] = -2*(empiricalMeasurements.get(reward1)-idleResult) * idleWithServiceTime * Math.exp(variables.getEntry(1)) +
  			  		  -2*(empiricalMeasurements.get(reward2)-servedRequestsResult) * servedRequestsWithServiceTime * Math.exp(variables.getEntry(1));
        //fDResult[0] = -2*idleWithRequestRate - 2*servedRequestsWithRequestRate;
        //fDResult[1] = -2*idleWithServiceTime - 2*servedRequestsWithServiceTime;

        return MatrixUtils.createRealVector(fDResult);
	}

	@Override
	public double ddf(double x) {
		System.err.println("FunctionSPDN.ddf(): We cannot count second derivative. :(");
		return 0;
	}

	@Override
	public RealMatrix DDf(RealVector variables) {
		System.err.println("FunctionSPDN.DDf(): We cannot count Hessian matrix. :(");
		return null;
	}

	@Override
	public int getDimension() {
		return 2;
	}

}
