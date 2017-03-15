
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;


/**
 * source: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 */
public class SimulatedAnnealing implements Optimizer {
	private double temp = 10000;
	private double coolingRate = 0.003;
	private double border = 0.01;
		
	@Override
	public double method(Function f) {
		return 0;
	}
	
	// Calculate the acceptance probability
	public double acceptanceProbability(double energy, double newEnergy) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp((energy - newEnergy) / temp);
    }
	
	
	@Override
	public RealVector Method(Function F) {
		
		RealVector xn = MatrixUtils.createRealVector(new double[F.getDimension()]);
		RealVector xnext = MatrixUtils.createRealVector(new double[F.getDimension()]);
		double fx = F.f(xn);
		double fxnext;
		RealVector best = xn;
		
		while (temp > 1) {
			for(int i=0; i<F.getDimension(); i++)
				xnext.setEntry(i, (xn.getEntry(i)-border)+Math.random()*2*border);
			fxnext = F.f(xnext);
			
			if (acceptanceProbability(fx,fxnext) > Math.random())
				xn = xnext;
			
			if (F.f(xn) < F.f(best))
				best = xn;
			
			temp *= 1-coolingRate;
		}
		
		return best;
	}

}
