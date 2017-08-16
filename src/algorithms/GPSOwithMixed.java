package algorithms;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import algorithms.ParticleSwarmOptimalization.Particle;
import functions.Function;
import other.CsvFileWriter;

public class GPSOwithMixed extends ParticleSwarmOptimalization{

	private RealVector L; // init of gradient descent
	private int gradIteration;
	private double gamma;
	
	public GPSOwithMixed(){
		swarmSize = 20;
		iteration = 20;
		gradIteration = 3;
		omega = 0.3;
		fiP = 0.4;
		fiG = 0.8;
		gamma = 0.5;
	}
	
	public RealVector Method(Function F) {
		System.out.println("GPSOwithMixed is started");
		
		ArrayList<Particle> swarm = new ArrayList<Particle>();
		putDownParticles(swarm,F);
		Random r = new Random();
		
		ArrayList<Double> results = new ArrayList<>();
		
		for(int i=0; i<iteration; i++){
			for (int j=0; j<swarmSize; j++){
				Particle p = swarm.get(j);
				RealVector x = p.getX().copy();
				RealVector v = p.getV().copy();
				RealVector best = p.getLocalBest().copy();
				
				for(int d=0; d<F.getDimension(); d++){
					v.setEntry(d, omega*v.getEntry(d)+fiP*r.nextDouble()*(best.getEntry(d)-x.getEntry(d))+fiG*r.nextDouble()*(globalBest.getEntry(d)-x.getEntry(d)));
					x.setEntry(d, x.getEntry(d)+v.getEntry(d));
				}
				
				p.step(x, v);
				double Ff = F.f(x);
				if (Ff < p.getLocalBestValue()){
					p.updateBest();
					if (Ff < globalBestF){
						globalBest = x.copy();
						globalBestF = F.f(globalBest);
					}
				}
			}
			
			RealVector temp = globalBest.copy();
			RealVector tempBefore = MatrixUtils.createRealVector(new double[F.getDimension()]);
			for(int j=0; j<gradIteration; j++){
				temp = temp.add(F.Df(temp).mapMultiply(-r.nextDouble()*gamma));
				RealVector DtempB = F.Df(tempBefore);
				RealVector Dtemp = F.Df(temp);
				gamma = temp.add(tempBefore.mapMultiply(-1)).dotProduct(Dtemp.add(DtempB.mapMultiply(-1)));
				gamma /= Dtemp.add(DtempB.mapMultiply(-1)).getNorm() * Dtemp.add(DtempB.mapMultiply(-1)).getNorm();
			}
			L = temp;
			
			double newValue = F.f(L);
			if(newValue < globalBestF){
				globalBest = L.copy();
				globalBestF = newValue;
			}
			
			results.add(globalBestF);
			
		}
		CsvFileWriter.addPSOMList(results);
		System.out.println("GPSO is done, calculated with local search and gradient descent, with " + swarmSize + " particle and " + iteration + " iteration.");
		return globalBest;
	}
}
