package functions;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;


public interface Function {
	public double f(RealVector variables);
	
	public double df(double x);
	
	// return gradient
	public RealVector Df(RealVector variables);
	
	public double ddf(double x);
	
	// return inverse of Hessian
	public RealMatrix DDf(RealVector variables);
	
	public int getDimension();
}
