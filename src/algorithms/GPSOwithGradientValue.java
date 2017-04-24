package algorithms;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.RealVector;

import functions.Function;

public class GPSOwithGradientValue extends ParticleSwarmOptimalization{

	private double fiGrad;
	
	public GPSOwithGradientValue(){
		swarmSize = 30;
		iteration = 10;
		omega = 0.3;
		fiP = 0.4;
		fiG = 0.8;
		fiGrad = 0.4;
	}
	
	public RealVector Method(Function F) { 
		System.out.println("GPSOwithGradientValue is started");
		
		ArrayList<Particle> swarm = new ArrayList<Particle>();
		putDownParticles(swarm,F);
		Random r = new Random();
		
		for (int i=0; i<iteration; i++){
			for (int j=0; j<swarmSize; j++){
				Particle p = swarm.get(j);
				RealVector x = p.getX().copy();
				RealVector v = p.getV().copy();
				RealVector best = p.getLocalBest().copy();
				RealVector g = F.Df(x);
				
				for(int d=0; d<F.getDimension(); d++){
					v.setEntry(d, omega*v.getEntry(d) + fiP*r.nextDouble()*(best.getEntry(d)-x.getEntry(d)) + fiG*r.nextDouble()*(globalBest.getEntry(d)-x.getEntry(d)) - fiGrad*g.getEntry(d) );
					x.setEntry(d, x.getEntry(d)+v.getEntry(d));
				}
					
				double Ff = F.f(x);
				if (Ff<p.getLocalBestValue()){
					p.updateBest();
					if (Ff<globalBestF){
						globalBest = x.copy();
						globalBestF = F.f(globalBest);
					}
				}
			}
		}
		
		System.out.println("GPSO is done, calculated with value of gradient, with " + swarmSize + " particle and " + iteration + " iteration.");
		return globalBest;
	}
	
}
