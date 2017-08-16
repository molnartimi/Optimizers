package functions;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;

import hu.bme.mit.inf.optimization.wrapper.breeze.DiffFunction;
import hu.bme.mit.inf.optimization.wrapper.breeze.ValueAndGradient;
import other.CsvFileWriter;
import functions.Function;

public class MyDiffFunction implements DiffFunction{
	private Function func;
	private int ctr = 0;
	private ArrayList<Double> results = new ArrayList<Double>();
	
	public void setFunction(Function f){
		func = f;
	}
	
	public ValueAndGradient calculate(double[] d){
		ctr++;
		double value = func.f(MatrixUtils.createRealVector(d));
		
		results.add(value);
		
		double[] gradient = new double[func.getDimension()];
		gradient = func.Df(MatrixUtils.createRealVector(d)).toArray();
		return new ValueAndGradient(value,gradient);
	}
	
	public int getCtr(){
		return ctr;
	}
	
	public void resetCtr(){
		ctr = 0;
	}
	
	public void writeOut(){
		CsvFileWriter.addBfgsList(results);
	}
	
}
