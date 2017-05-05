package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import main.CsvFileWriter;

public class BeesAlgorithm implements Optimizer {
	private int ns = 10;	//scout
	private int nb = 5;	//best sites
	private int ne = 2;		//elite sites
	private int nre = 10;	//recruited for elite sites
	private int nrb = 5;	//recruited for best sites
	
	private int maxIter = 10;
	private double border = 5;
	private double initRadius = 0.3;
	private double smallerRate = 0.7;
	
	private class Bee{
		private RealVector pos;
		private double value;
		
		public Bee(RealVector p, double val){
			pos = p;
			value = val;
		}
		
		public void setPos(RealVector p, double val){
			pos = p;
			value = val;
		}
		
		public RealVector getPos(){
			return pos;
		}
		
		public double getVal(){
			return value;
		}
	}
	
	private void init(Function f, ArrayList<Bee> bees){
		Random r = new Random();
		
		for(int i=0; i<ns; i++) {
			RealVector pos = MatrixUtils.createRealVector(new double[f.getDimension()]);
			for (int d=0; d<f.getDimension(); d++){
				pos.setEntry(d, r.nextDouble()*border);
			}
			bees.add(new Bee(pos, f.f(pos)));
		}
	}
	
	private class BeeComparator implements Comparator<Bee> {
		@Override
		public int compare(Bee b1, Bee b2) {
			double result = b1.getVal() - b2.getVal();
			if (result < 0)
				return -1;
			else if (result == 0)
				return 0;
			else
				return 1;
		}
	}
	
	@Override
	public RealVector Method(Function F) {
		System.out.println("Bees algorithm is started");
		
		ArrayList<Double> results = new ArrayList<>();
		
		Random r = new Random();
		
		ArrayList<Bee> scouts = new ArrayList<Bee>();
		ArrayList<Bee> eliteRecruited = new ArrayList<Bee>();
		ArrayList<Bee> bestRecruited = new ArrayList<Bee>();
		
		double R = initRadius;
		
		init(F, scouts);
		
		Collections.sort(scouts, new BeeComparator());
		
		int ctr = 0;
		
		for (int iter = 0; iter < maxIter; iter++) {

			// elite bees and their foragers
			for (int i = 0; i < ne; i++) {
				RealVector localBest = scouts.get(i).getPos().copy();
				for (int j = 0; j < nre; j++) {
					RealVector pos = MatrixUtils.createRealVector(new double[F.getDimension()]);
					for (int d = 0; d < F.getDimension(); d++) {
						pos.setEntry(d, localBest.getEntry(d) + (r.nextDouble()*2-1) * R);
					}
					double newPosValue = F.f(pos);
					if (newPosValue < scouts.get(i).getVal()) {
						localBest = pos;
						eliteRecruited.add(scouts.get(i));
						scouts.set(i, new Bee(pos, newPosValue));
					} else {
						eliteRecruited.add(new Bee(pos, newPosValue));
					}
				}
				ctr++;
			}

			// best bees and their foragers
			for (int i = ne; i < nb; i++) {
				RealVector localBest = scouts.get(i).getPos().copy();
				for (int j = 0; j < nrb; j++) {
					RealVector pos = MatrixUtils.createRealVector(new double[F.getDimension()]);
					for (int d = 0; d < F.getDimension(); d++) {
						pos.setEntry(d, localBest.getEntry(d) + r.nextDouble() * R);
					}
					double newPosValue = F.f(pos);
					if (newPosValue < scouts.get(i).getVal()) {
						localBest = pos;
						bestRecruited.add(scouts.get(i));
						scouts.set(i, new Bee(pos, newPosValue));
					} else {
						bestRecruited.add(new Bee(pos, newPosValue));
					}
					ctr++;
				}
			}

			// global search bees
			for (int i = nb; i < ns; i++) {
				RealVector pos = MatrixUtils.createRealVector(new double[F.getDimension()]);
				for (int d = 0; d < F.getDimension(); d++) {
					pos.setEntry(d, r.nextDouble() * border);
				}
				scouts.get(i).setPos(pos, F.f(pos));
				ctr++;
			}

			Collections.sort(scouts, new BeeComparator());
			
			results.add(scouts.get(0).getVal());
			
			R *= smallerRate;
		}
		//CsvFileWriter.writeOut("BeesAlgorithm3.csv", results);
		System.out.println("Iteration number of Bees algorithm: "+ctr);
		return scouts.get(0).getPos();
	}

}
