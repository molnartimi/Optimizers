package algorithms;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import algorithms.ParticleSwarmOptimalization.Particle;
import functions.Function;

public class PSOwithGD extends ParticleSwarmOptimalization{
/*	protected int swarmSize = 100;
	protected double border = 10;
	protected int iteration = 20;
	protected RealVector globalBest = null; 
	
	protected double omega = 0.1;
	protected double fiP = 0.2;
	protected double fiG = 0.8;
*/
	private double fiGrad;
	private double gamma;
	
	public PSOwithGD(){
		super();
		iteration = 10;
		
		omega = 0.1;
		fiP = 0.2;
		fiG = 0.3;
		fiGrad = 0.4;
		gamma = 0.1;
	}
	
	public RealVector Method(Function F) { 
		System.out.println("PSO with Gradient descent is started");
		
		ArrayList<Particle> swarm = new ArrayList<Particle>();
		putDownParticles(swarm,F);
		Random r = new Random();
		
		for (int i=0; i<iteration; i++){
			for (int j=0; j<swarmSize; j++){
				
				Particle p = swarm.get(j);
				RealVector x = p.getX().copy();
				RealVector v = p.getV().copy();
				RealVector best = p.getLocalBest().copy();
				
				
				// With simple gradient descent
				//x = x.add(F.Df(x).mapMultiply(-gamma));
				//gamma = gamma*2 > iteration ? gamma*2 : gamma;
				
				// With gradient
				RealVector g = F.Df(x);
				for(int d=0; d<F.getDimension(); d++){
					v.setEntry(d, omega*v.getEntry(d) + fiP*r.nextDouble()*(best.getEntry(d)-x.getEntry(d)) + fiG*r.nextDouble()*(globalBest.getEntry(d)-x.getEntry(d)) - fiGrad*g.getEntry(d) );
					x.setEntry(d, x.getEntry(d)+v.getEntry(d));
				}
				
				double Ff = F.f(x);
				if (Ff<F.f(p.getLocalBest())){
					p.updateBest();
					if (Ff<globalBestF){
						globalBest = x.copy();
						globalBestF = F.f(globalBest);
					}
				}	
			}
		}
		
		
		System.out.println("PSO with gradient descent is done with " + swarmSize + " particle and " + iteration + " iteration.");
		//System.out.println("((  Gradient here: " + F.Df(globalBest).toString() + "  ))");
		return globalBest;
	}
}
