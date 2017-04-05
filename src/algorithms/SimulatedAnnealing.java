package algorithms;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;


/**
 * source: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 */
public class SimulatedAnnealing implements Optimizer {
	private double  inittemp = 1000;
	private double coolingRate = 0.01;
	private double border = 1;
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
		int ctr=0;
		double temp = inittemp;
		RealVector xn = MatrixUtils.createRealVector(new double[F.getDimension()]);
		xn.set(0.5);
		
		RealVector xnext = MatrixUtils.createRealVector(new double[F.getDimension()]);
		double fx = F.f(xn);
		double fxnext;
		RealVector best = xn.copy();
		
			while (temp > 1) {
				
				for(int j=0;j<iteration;j++){
				
					for(int i=0; i<F.getDimension(); i++)
						xnext.setEntry(i, (xn.getEntry(i)-border)+Math.random()*2*border);
					fxnext = F.f(xnext);
					
					if (acceptanceProbability(fx,fxnext,temp) > Math.random())
						xn = xnext.copy();
					
					if (F.f(xn) < F.f(best))
						best = xn.copy();
					
					ctr++;
				}
					
				temp *= 1-coolingRate;
			}
		
		System.out.println("Iteration number of Simulated Annealing: "+ctr);
		
		return best;
	}

}
