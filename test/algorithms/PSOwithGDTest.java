package algorithms;

import static org.junit.Assert.assertTrue;

import org.apache.commons.math3.linear.RealVector;
import org.junit.Ignore;
import org.junit.Test;

import functions.ExampleFunction;
import main.Main;

public class PSOwithGDTest extends EasyTest{
	public PSOwithGDTest(){
		opt = new PSOwithGD();
	}
	
	@Test
	public void test1(){
		// z = x^2+y^2-x
		f = new ExampleFunction(4);
		RealVector result = opt.Method(f);
		
		// expected result: {0,5;0}
		tolerance = new double[2];
		tolerance[0]=Math.abs(result.getEntry(0)-0.5);
		tolerance[1]=Math.abs(result.getEntry(1));
		
		boolean ok = false;
		if (tolerance[0] < Main.epszilon && tolerance[1] < Main.epszilon)
			ok = true;
		
		printTolerance();
		assertTrue(ok);
	}
	
	@Test
	public void test1b(){
		// z = x^2+y^2-x
		((PSOwithGD)opt).version2();
		f = new ExampleFunction(4);
		RealVector result = opt.Method(f);
		
		// expected result: {0,5;0}
		tolerance = new double[2];
		tolerance[0]=Math.abs(result.getEntry(0)-0.5);
		tolerance[1]=Math.abs(result.getEntry(1));
		
		boolean ok = false;
		if (tolerance[0] < Main.epszilon && tolerance[1] < Main.epszilon)
			ok = true;
		
		printTolerance();
		assertTrue(ok);
	}
	
	@Test
	public void test2(){
		// v = x^2+y^2+(z-1)^2
		f = new ExampleFunction(5);
		RealVector result = opt.Method(f);
		
		// expected result: {0;0;1}
		tolerance = new double[3];
		tolerance[0]=Math.abs(result.getEntry(0));
		tolerance[1]=Math.abs(result.getEntry(1));
		tolerance[2]=Math.abs(result.getEntry(2) - 1);
		
		boolean ok = false;
		if (tolerance[0] < Main.epszilon && tolerance[1] < Main.epszilon && tolerance[2] < Main.epszilon)
			ok = true;
		
		printTolerance();
		assertTrue(ok);
	}
	
	@Test
	public void test2b(){
		// v = x^2+y^2+(z-1)^2
		f = new ExampleFunction(5);
		((PSOwithGD)opt).version2();
		RealVector result = opt.Method(f);
		
		// expected result: {0;0;1}
		tolerance = new double[3];
		tolerance[0]=Math.abs(result.getEntry(0));
		tolerance[1]=Math.abs(result.getEntry(1));
		tolerance[2]=Math.abs(result.getEntry(2) - 1);
		
		boolean ok = false;
		if (tolerance[0] < Main.epszilon && tolerance[1] < Main.epszilon && tolerance[2] < Main.epszilon)
			ok = true;
		
		printTolerance();
		assertTrue(ok);
	}
	
	@Test
	public void test3(){
		// y = sin(x)+cos(y)
		f = new ExampleFunction(3);
		RealVector result = opt.Method(f);
		
		// expected result: {4.71238898+2n*pi;pi+2n*pi}
		
		while(result.getEntry(0)-2*Math.PI >= 0)
			result.setEntry(0, result.getEntry(0)-2*Math.PI);
		while(result.getEntry(1)-2*Math.PI >= 0)
			result.setEntry(1, result.getEntry(1)-2*Math.PI);
		while(result.getEntry(0) < 0)
			result.setEntry(0, result.getEntry(0)+2*Math.PI);
		while(result.getEntry(1) < 0)
			result.setEntry(1, result.getEntry(1)+2*Math.PI);
		
		tolerance = new double[2];
		tolerance[0]=Math.abs(result.getEntry(0)-4.71238898);
		tolerance[1]=Math.abs(result.getEntry(1)-Math.PI);
		
		boolean ok = false;
		if (tolerance[0] < Main.epszilon && tolerance[1] < Main.epszilon)
			ok = true;
		
		printTolerance();
		assertTrue(ok);
	}
	
	@Test
	public void test3b(){
		// y = sin(x)+cos(y)
		f = new ExampleFunction(3);
		((PSOwithGD)opt).version2();
		RealVector result = opt.Method(f);
		
		// expected result: {4.71238898+2n*pi;pi+2n*pi}
		
		while(result.getEntry(0)-2*Math.PI >= 0)
			result.setEntry(0, result.getEntry(0)-2*Math.PI);
		while(result.getEntry(1)-2*Math.PI >= 0)
			result.setEntry(1, result.getEntry(1)-2*Math.PI);
		while(result.getEntry(0) < 0)
			result.setEntry(0, result.getEntry(0)+2*Math.PI);
		while(result.getEntry(1) < 0)
			result.setEntry(1, result.getEntry(1)+2*Math.PI);
		
		tolerance = new double[2];
		tolerance[0]=Math.abs(result.getEntry(0)-4.71238898);
		tolerance[1]=Math.abs(result.getEntry(1)-Math.PI);
		
		boolean ok = false;
		if (tolerance[0] < Main.epszilon && tolerance[1] < Main.epszilon)
			ok = true;
		
		printTolerance();
		assertTrue(ok);
	}
}
