package algorithms;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import main.Main;

public class GradientAlgorithm implements Optimizer {

	@Override
	public double method(Function f) {
		return 0;
	}

	@Override
	public RealVector Method(Function F) {
		
		System.out.println("Gradient descent is started");
		
		double gamma = 1.0;
		RealVector xn = MatrixUtils.createRealVector(new double[F.getDimension()]);
		xn.set(0.5);
		RealVector xnBefore;
		RealVector xnNext;
		
		xnNext = xn.add(F.Df(xn).mapMultiply(-gamma));
		int ctr = 1;
		
		while (F.Df(xnNext).getNorm() >= Main.epszilon){
			xnBefore = xn.copy();
			xn = xnNext.copy();
			
			RealVector DxB = F.Df(xnBefore);
			RealVector Dx = F.Df(xn);
			
			gamma = xn.add(xnBefore.mapMultiply(-1)).dotProduct(Dx.add(DxB.mapMultiply(-1)));
			gamma /= Dx.add(DxB.mapMultiply(-1)).getNorm() * Dx.add(DxB.mapMultiply(-1)).getNorm();
			
			xnNext = xn.add(Dx.mapMultiply(-gamma));
			ctr++;
		}
		
		System.out.println("Iteration number of Gradient descent algorithm: "+ctr);
		return xnNext;
	}

}
