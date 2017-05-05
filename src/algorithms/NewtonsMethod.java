package algorithms;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import test.AlgorithmTest;

public class NewtonsMethod implements Optimizer {
	
	@Override
	public double method(Function F) {
		
		double xn = 0;
		double dx = F.df(xn);
		double ddx = F.ddf(xn);
		int ctr = 0;
		
		do{
			if (ddx == 0){
				xn += AlgorithmTest.epszilon;
				dx = F.df(xn);
				ddx = F.ddf(xn);
				continue;
			}
			xn-=dx/ddx;
			ctr++;

			dx = F.df(xn);
			ddx = F.ddf(xn);	
		} while(Math.abs(dx) >= AlgorithmTest.epszilon);
		
		System.out.println("Iteration number of Newton's method: "+ctr);
		return xn;
	}

	@Override
	public RealVector Method(Function F) {
		
		double gamma = 1.0;
		RealVector xn = MatrixUtils.createRealVector(new double[F.getDimension()]);
		xn.set(0);
		RealVector Dx = F.Df(xn);
		RealMatrix DDx;
		int ctr = 0;
		
		do {
			try {
				DDx = F.DDf(xn);
				xn = xn.add(DDx.operate(Dx).mapMultiply(-gamma));
				ctr++;
			} catch(Exception e){
				if (e.getMessage().equals("matrix is singular"))
					xn = xn.mapAdd(0.001);
				else
					System.err.println(e.getMessage());
			}
			Dx = F.Df(xn);
		} while (Dx.getNorm() >= AlgorithmTest.epszilon);
		
		System.out.println("Iteration number of Newton's method: "+ctr);
		return xn;
	}

}
