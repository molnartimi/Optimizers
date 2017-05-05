package algorithms;

import org.apache.commons.math3.linear.RealVector;

import functions.Function;

public interface Optimizer {
	// for higher-dimensional problem
	public RealVector Method(Function F);
}
