package test;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

import functions.FunctionSPDN;

public class TestFuncVsGrad {
	private int m = 10000;
	private FunctionSPDN f;
	private RealVector v;
	
	public TestFuncVsGrad(){
		f = AlgorithmTest.setModel("E:/Timi/BME/6.félév/Önálló laboratórium/Optimizers",
				"src/models/simple-server.pnml",
				new String[]{"requestRate", "serviceTime"},
				new String[]{"Idle", "ServedRequests"},
				new double[]{0.727272727272727, 1.09090909090909});
		v = MatrixUtils.createRealVector(new double[2]);
		v.set(0.5);
	}
	
	@Test
	public void funcTest(){
		for(int i=0;i<m;i++){
			f.f(v);
		}
	}
	
	@Test
	public void gradTest(){
		for(int i=0;i<m;i++){
			f.Df(v);
		}
	}
	
	
}
