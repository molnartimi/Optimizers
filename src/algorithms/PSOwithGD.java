package algorithms;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;

public class PSOwithGD extends ParticleSwarmOptimalization{
/*	protected int swarmSize = 100;
	protected double border = 10;
	protected int iteration = 100;
	protected RealVector globalBest = null; */
	
	public RealVector Method(Function F) { 
		ArrayList<Particle> swarm = new ArrayList<Particle>();
		putDownParticles(swarm,F);
		
		for (int i=0; i<iteration; i++){
			for (int j=0; j<swarmSize; j++){
				double gamma = 0.1;
				Particle p = swarm.get(j);
				RealVector x = p.getX();
				x = x.add(F.Df(x).mapMultiply(-gamma));
				gamma = gamma*2 > iteration ? gamma*2 : gamma;
				
				
				p.step(x, MatrixUtils.createRealVector(new double[1]));
				if (F.f(x)<F.f(p.getLocalBest())){
					p.updateBest();
					if (F.f(x)<F.f(globalBest))
						globalBest = x.copy();
				}	
			}
		}
		
		System.out.println("PSO with gradient descent is done with " + swarmSize + " particle and " + iteration + " iteration.");
		//System.out.println("((  Gradient here: " + F.Df(globalBest).toString() + "  ))");
		return globalBest;
	}
}
