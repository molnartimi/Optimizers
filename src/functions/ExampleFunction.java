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
		// y = sin(x)+cos(y)
		if (id == 2) return Math.sin(variables.getEntry(0))+Math.cos(variables.getEntry(1));
		
		// z = x^2+y^2-x
		if (id == 3) return variables.getEntry(0)*variables.getEntry(0)+variables.getEntry(1)*variables.getEntry(1)-variables.getEntry(0);
		
		// k = x^2+y^2+(z-1)^2
		if (id == 4) return variables.getEntry(0)*variables.getEntry(0)+variables.getEntry(1)*variables.getEntry(1)+(variables.getEntry(2)-1)*(variables.getEntry(2)-1);
		return 0;
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
		return 0;
	}
	
	

}
