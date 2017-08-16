package algorithms;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import functions.MyDiffFunction;
import hu.bme.mit.inf.optimization.wrapper.breeze.LbfgsWrapper;
import test.AlgorithmTest;


public class MyLBFGS implements Optimizer {
	private int m = 4;
	private int maxIter = 20;
	private double tolerance = AlgorithmTest.epszilon;
	
	public MyLBFGS(){}
	public MyLBFGS(int iter){
		maxIter = iter;
	}

	@Override
	public RealVector Method(Function F) {
		System.out.println("L-BFGS is started");
		
		LbfgsWrapper lbfgs = new LbfgsWrapper(maxIter,m,tolerance);
		MyDiffFunction diffF = new MyDiffFunction();
		diffF.setFunction(F);
		double[] xn = new double[F.getDimension()];
		for(int i=0;i<F.getDimension();i++)
			xn[i] = 3;
		
		double[] result = lbfgs.minimize(diffF, xn);
		
		diffF.writeOut();
		System.out.println("Iteration number of L-BFGS: " + diffF.getCtr());
		diffF.resetCtr();
		
		return MatrixUtils.createRealVector(result);
	}

}
