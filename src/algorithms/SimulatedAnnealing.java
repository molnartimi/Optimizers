package algorithms;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import main.CsvFileWriter;


/**
 * source: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 */
public class SimulatedAnnealing implements Optimizer {
	private double  inittemp = 100;
	private double coolingRate = 0.1;
	private double border = 0.3;
	private double borderRate = 0.05;
	private int iteration = 3;
		
	@Override
	public double method(Function F) {
		return Method(F).getEntry(0);
	}
	
	// Calculate the acceptance probability
	public double acceptanceProbability(double energy, double newEnergy, double temp) {
        if (newEnergy < energy) {
            return 1.0;
        }
        double result = Math.exp((energy - newEnergy) / temp);
        return result;
    }
	
	
	@Override
	public RealVector Method(Function F) {
		System.out.println("Simulated annealing is started");
		
		ArrayList<Double> results = new ArrayList<>();
		
		int ctr=0;
		double temp = inittemp;
		RealVector xn = MatrixUtils.createRealVector(new double[F.getDimension()]);
		xn.set(0.5);
		
		RealVector xnext = MatrixUtils.createRealVector(new double[F.getDimension()]);
		double fx = F.f(xn);
		double bestF = fx;
		double fxnext;
		RealVector best = xn.copy();
		
			while (temp > 1) {
				
				for(int j=0;j<iteration;j++){
				
					for(int i=0; i<F.getDimension(); i++)
						xnext.setEntry(i, (xn.getEntry(i)-border)+Math.random()*2*border);
					fxnext = F.f(xnext);
					
					if (acceptanceProbability(fx,fxnext,temp) > Math.random())
						xn = xnext.copy();
					
					if (F.f(xn) < bestF){
						best = xn.copy();
						bestF = F.f(best);
					}
					
					ctr++;
				}
					
				temp *= 1-coolingRate;
				border *= 1-borderRate;
				
				results.add(bestF);
			}
		//CsvFileWriter.writeOut("SimulatedAnnealing6.csv", results);
		System.out.println("Iteration number of Simulated Annealing: "+ctr);
		
		return best;
	}

}
