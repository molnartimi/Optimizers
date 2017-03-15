import org.apache.commons.math3.linear.RealVector;

public interface Optimizer {
	// for one-dimensional problem
	public double method(Function f);
	// for higher-dimensional problem
	public RealVector Method(Function F);
}
