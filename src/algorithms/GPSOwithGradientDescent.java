package algorithms;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.RealVector;

import algorithms.ParticleSwarmOptimalization.Particle;
import functions.Function;

public class GPSOwithGradientDescent extends ParticleSwarmOptimalization {
	
	private double gamma;
	
	public GPSOwithGradientDescent(){
		swarmSize = 30;
		iteration = 10;
		gamma = 0.5;
	}
	
	public RealVector Method(Function F) { 
		System.out.println("GPSOwithGradientDescent is started");
		
		ArrayList<Particle> swarm = new ArrayList<Particle>();
		putDownParticles(swarm,F);
		Random r = new Random();
		
		for (int i=0; i<iteration; i++){
			for (int j=0; j<swarmSize; j++){
				Particle p = swarm.get(j);
				RealVector x = p.getX().copy();	
				x = x.add(F.Df(x).mapMultiply(-r.nextDouble()*gamma));
				
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
		
		System.out.println("GPSO is done, calculated with gradient descent, with " + swarmSize + " particle and " + iteration + " iteration.");
		return globalBest;
	}

}
