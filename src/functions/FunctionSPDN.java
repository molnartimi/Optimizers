package functions;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hu.bme.mit.inf.petridotnet.spdn.AnalysisBuilder;
import hu.bme.mit.inf.petridotnet.spdn.AnalysisResult;
import hu.bme.mit.inf.petridotnet.spdn.Parameter;
import hu.bme.mit.inf.petridotnet.spdn.Reward;
import hu.bme.mit.inf.petridotnet.spdn.SpdnAnalyzer;

public class FunctionSPDN implements Function {
	
	private SpdnAnalyzer analyzer;
	private AnalysisBuilder builder;
	private List<Parameter> parameters;
	private List<Reward> rewards;
	private Map<Reward,Double> empiricalMeasurements;
	private int fctr = 0;
	private int dctr = 0;
	
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
		builder = analyzer.createAnalysisBuilder();
		setRewards(builder);
	}
	
	private AnalysisResult runAnalyzer(RealVector variables){
        System.err.println(fctr + ", " + dctr);
        setParams(builder,variables);
        
        AnalysisResult result = builder.run();
    
        return result;
	}
		
	
	private void setRewards(AnalysisBuilder builder) {
		for(int i=0; i<rewards.size(); i++){
			builder.withReward(rewards.get(i), parameters);
		}
		
	}

	private void setParams(AnalysisBuilder builder, RealVector variables) {
		for(int i=0; i<parameters.size(); i++){
			builder = builder.withParameter(parameters.get(i), Math.exp(variables.getEntry(i)));
		}
		
	}

	@Override
	public double f(RealVector variables) {
		fctr++;
		
		AnalysisResult result = runAnalyzer(variables);
		
		double resultF = 0;
		for(int i=0; i<rewards.size(); i++){
			resultF += Math.pow(empiricalMeasurements.get(rewards.get(i)) - result.getValue(rewards.get(i)), 2);
		}
		
		return resultF;
	}

	@Override
	public RealVector Df(RealVector variables) {
		dctr++;
		
        double[] fDResult = new double[getDimension()];
        
        AnalysisResult result = runAnalyzer(variables);
        
        for(int i=0; i<fDResult.length; i++){
			fDResult[i] = 0;
		}
		
		for (int i=0; i<fDResult.length; i++){
			for(int j=0; j<rewards.size(); j++){
				fDResult[i] += -2 * (empiricalMeasurements.get(rewards.get(j)) - result.getValue(rewards.get(j))) * 
						result.getSensitivity(rewards.get(j), parameters.get(i)) * 
						Math.exp(variables.getEntry(i));
			}
		}
        
        return MatrixUtils.createRealVector(fDResult);
	}
	

	@Override
	public RealMatrix DDf(RealVector variables) {
		System.err.println("FunctionSPDN.DDf(): We cannot count Hessian matrix. :(");
		return null;
	}

	@Override
	public int getDimension() {
		return parameters.size();
	}
	
	public int getFctr(){
		return fctr;
	}
	
	public int getDctr(){
		return dctr;
	}
	
	public void restartCtrs(){
		fctr = 0;
		dctr = 0;
	}

}
