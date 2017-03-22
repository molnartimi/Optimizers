package algorithms;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import main.Main;
import used.LBFGS;

public class MyLBFGS implements Optimizer {
	private int m = 4;
	private int[] iprint = new int[]{/*1, 3*/ -1, 0};
	private int[] iflag = new int[]{0};
	
	// An estimate of the machine precision.
	private double xtol = Math.pow(2, -52);
	
	@Override
	public double method(Function F) {
		int n = 1;
		double x[] = new double[]{0};
		double f = F.f(MatrixUtils.createRealVector(x));
		double g[] = new double[]{F.df(x[0])};
		
		try {
			LBFGS.lbfgs(n, m, x, f, g, false, new double[n], iprint, Main.epszilon, xtol, iflag);
			while (iflag[0] != 0) {
				f = F.f(MatrixUtils.createRealVector(x));
				g = new double[]{F.df(x[0])};
				LBFGS.lbfgs(n, m, x, f, g, false, new double[n], iprint, Main.epszilon, xtol, iflag);
			}
		} catch (LBFGS.ExceptionWithIflag e) {
			e.printStackTrace();
		}
		
		return LBFGS.solution_cache[0];
	}

	@Override
	public RealVector Method(Function F) {
		int n = F.getDimension();
		double[] xArray = new double[n];
		RealVector x = MatrixUtils.createRealVector(xArray);
		double f = F.f(x);
		RealVector g = F.Df(x);
		try {
			iflag[0] = 0;
			LBFGS.lbfgs(n, m, xArray, f, g.toArray(), false, new double[n], iprint, Main.epszilon, xtol, iflag);
			while (iflag[0] != 0) {
				x = MatrixUtils.createRealVector(xArray);
				f = F.f(x);
				g = F.Df(x);
				System.out.println("x = " + x.toString());
				System.out.println("f = " + f);
				System.out.println("g = " + g.toString());
				System.out.println("---" + iflag[0]);
				LBFGS.lbfgs(n, m, xArray, f, g.toArray(), false, new double[n], iprint, Main.epszilon, xtol, iflag);
			}
		} catch (LBFGS.ExceptionWithIflag e) {
			e.printStackTrace();
		}
		
		return MatrixUtils.createRealVector(LBFGS.solution_cache);
	}

}
