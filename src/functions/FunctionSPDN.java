package functions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hu.bme.mit.inf.petridotnet.spdn.AnalysisConfiguration;
import hu.bme.mit.inf.petridotnet.spdn.AnalysisResult;
import hu.bme.mit.inf.petridotnet.spdn.Parameter;
import hu.bme.mit.inf.petridotnet.spdn.Reward;
import hu.bme.mit.inf.petridotnet.spdn.SimpleServerTest;
import hu.bme.mit.inf.petridotnet.spdn.Spdn;
import hu.bme.mit.inf.petridotnet.spdn.SpdnAnalyzer;
import main.Main;

public class FunctionSPDN implements Function {
	SpdnAnalyzer analyzer;
	List<Parameter> parameters;
	Map<Reward,Double> empiricalMeasurements;
	
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
	public FunctionSPDN(SpdnAnalyzer analyzer, List<Parameter> parameters, Map<Reward, Double> empiricalMeasurements) {
		this.analyzer = analyzer;
		this.parameters = parameters;
		this.empiricalMeasurements = empiricalMeasurements;
		
	}
	
	private AnalysisResult runAnalyzer(RealVector variables){
		if(variables.getNorm()==0){
			System.err.println("Variables in FunctionSPDN.runAnalyzer is {0;0}! I set it to epszilon.");
			variables.set(Main.epszilon);
		}
		
		Reward reward1 = (Reward)empiricalMeasurements.keySet().toArray()[0];
        Reward reward2 = (Reward)empiricalMeasurements.keySet().toArray()[1];
        
        AnalysisResult result = analyzer.createAnalysisBuilder()
                    .withParameter(parameters.get(0), variables.getEntry(0))
                    .withParameter(parameters.get(1), variables.getEntry(1))
                    .withReward(reward1,parameters.get(0), parameters.get(1))
                    .withReward(reward2,parameters.get(0), parameters.get(1))
                    .run();
        
        return result;
	}
		
	
	@Override
	public double f(RealVector variables) {
        double fResult = 0;
        
        Reward reward1 = (Reward)empiricalMeasurements.keySet().toArray()[0];
        Reward reward2 = (Reward)empiricalMeasurements.keySet().toArray()[1];
        
        AnalysisResult result = runAnalyzer(variables);
            
        double idleResult = result.getValue(reward1);
        double servedRequestsResult = result.getValue(reward2);
            
        fResult = 	(empiricalMeasurements.get(reward1)-idleResult)*(empiricalMeasurements.get(reward1)-idleResult) + 
            		(empiricalMeasurements.get(reward2)-servedRequestsResult)*(empiricalMeasurements.get(reward2)-servedRequestsResult);
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
        
        Reward reward1 = (Reward)empiricalMeasurements.keySet().toArray()[0];
        Reward reward2 = (Reward)empiricalMeasurements.keySet().toArray()[1];
        
        AnalysisResult result = runAnalyzer(variables);
            
        double idleRequestRate = result.getSensitivity(reward1, parameters.get(0));
        double idleServiceTime = result.getSensitivity(reward1, parameters.get(1));
        double servedRequestsRequestRate = result.getSensitivity(reward2, parameters.get(0));
        double servedRequestsServiceTime = result.getSensitivity(reward2, parameters.get(1));
            
        fDResult[0] = -2*idleRequestRate - 2*servedRequestsRequestRate;
        fDResult[1] = -2*idleServiceTime - 2*servedRequestsServiceTime;

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
