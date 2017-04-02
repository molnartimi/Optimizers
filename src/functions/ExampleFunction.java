package functions;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class ExampleFunction implements Function {
	
	private int id = 0;

	public ExampleFunction(int funcId) {
		id = funcId-1;
	}

	@Override
	public double f(RealVector variables) {
		// y = (x-3)^2
		if (id == 0) return (variables.getEntry(0)-3)*(variables.getEntry(0)-3);
		
		// y = (x-21)^2+5x
		if (id == 1) return (variables.getEntry(0)-21)*(variables.getEntry(0)-21)+5*variables.getEntry(0);
		
		// y = sin(x)+cos(y)
		if (id == 2) return Math.sin(variables.getEntry(0))+Math.cos(variables.getEntry(1));
		
		// z = x^2+y^2-x
		if (id == 3) return variables.getEntry(0)*variables.getEntry(0)+variables.getEntry(1)*variables.getEntry(1)-variables.getEntry(0);
		
		// k = x^2+y^2+(z-1)^2
		if (id == 4) return variables.getEntry(0)*variables.getEntry(0)+variables.getEntry(1)*variables.getEntry(1)+(variables.getEntry(2)-1)*(variables.getEntry(2)-1);
		
		// y = sin(x)
		if (id == 5) return Math.sin(variables.getEntry(0));
		return -1;
	}

	@Override
	public double df(double x) {
		if (id == 0) return 2*(x-3);
		if (id == 1) return 2*(x-21)+5;
		if (id == 5) return Math.cos(x);
		return -1;
	}
	
	@Override
	public RealVector Df(RealVector variables) {
		double[] result=new double[2];
		if (id == 2) {
			result[0] = Math.cos(variables.getEntry(0));
			result[1] = -Math.sin(variables.getEntry(1));
		}
		if (id == 3) {
			result[0] = 2*variables.getEntry(0)-1; 
			result[1] = 2*variables.getEntry(1); 
		}
		if (id == 4){
			result = new double[3];
			result[0] = 2*variables.getEntry(0);
			result[1] = 2*variables.getEntry(1);
			result[2] = 2*(variables.getEntry(2)-1);
		}
		return MatrixUtils.createRealVector(result);
	}
	
	@Override
	public double ddf(double x) {
		if (id == 0) return 2;
		if (id == 1) return 2;
		if (id == 5) return -Math.sin(x);
		return -1;
	}

	@Override
	public RealMatrix DDf(RealVector variables) {
		RealMatrix result = MatrixUtils.createRealMatrix(new double[variables.getDimension()][variables.getDimension()]);
		if (id == 2){
			result.setEntry(0, 0, -Math.sin(variables.getEntry(0)));
			result.setEntry(0, 1, 0);
			result.setEntry(1, 0, 0);
			result.setEntry(1, 1, -Math.cos(variables.getEntry(1)));
		}
		if (id == 3){
			result.setEntry(0, 0, 2);
			result.setEntry(0, 1, 0);
			result.setEntry(1, 0, 0);
			result.setEntry(1, 1, 2);
		}
		if (id == 4){
			result.setEntry(0, 0, 2);
			result.setEntry(0, 1, 0);
			result.setEntry(0, 2, 0);
			result.setEntry(1, 0, 0);
			result.setEntry(1, 1, 2);
			result.setEntry(1, 2, 0);
			result.setEntry(2, 0, 0);
			result.setEntry(2, 1, 0);
			result.setEntry(2, 2, 2);
		}
		return MatrixUtils.inverse(result);
		
	}

	@Override
	public int getDimension() {
		if (id == 3 || id == 2) return 2;
		if (id == 4) return 3;
		return 1;
	}
	
	

}
