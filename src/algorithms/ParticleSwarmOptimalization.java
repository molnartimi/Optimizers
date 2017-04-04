package algorithms;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;

public class ParticleSwarmOptimalization implements Optimizer {
	protected int swarmSize = 100;
	protected double border = 10;
	protected int iteration = 20;
	protected RealVector globalBest = null;
	
	protected double omega = 0.1;
	protected double fiP = 0.2;
	protected double fiG = 0.8;
	
	protected class Particle {
		private RealVector x;
		private RealVector v;
		private RealVector best;
		
		public Particle (RealVector pos, RealVector vel){
			x = pos;
			best = pos;
			v = vel;
			
		}
		
		public void updateBest() {
			best = x;
		}
		
		public RealVector getLocalBest() {
			return best;
		}
		
		public RealVector getV(){
			return v;
		}
		
		public RealVector getX(){
			return x;
		}
		
		public void step(RealVector newX, RealVector newV){
			x = newX.copy();
			v = newV.copy();
		}
		
	}
	
	protected void putDownParticles(ArrayList<Particle> swarm,Function F){
		Random r = new Random();
		double interval = 2*border/Math.sqrt(swarmSize);
		double posX = -border;
		double posY = -border;
		
		for(int i=0; i<swarmSize; i++) {
			
			RealVector pos = MatrixUtils.createRealVector(new double[F.getDimension()]);
			RealVector vel = MatrixUtils.createRealVector(new double[F.getDimension()]);
			
			if (posX < border){
				pos.setEntry(0, posX);
				posX += interval;
			}
			else{
				posX = -border;
				posY += interval;
			}
			
			if (posY < border){
				pos.setEntry(1, posY);
			}
			
			for (int d=0; d<F.getDimension(); d++){
				//pos.setEntry(d, r.nextDouble()*2*border-border);
				vel.setEntry(d, r.nextDouble()*4*border-2*border);
			}
			
			Particle p = new Particle(pos,vel);
			swarm.add(p);
			
			if ( globalBest == null || F.f(pos)<F.f(globalBest))
				globalBest = pos.copy();
		}
	}
	
	@Override
	public double method(Function f) {
		return 0;
	}

	@Override
	public RealVector Method(Function F) {
		
		ArrayList<Particle> swarm = new ArrayList<Particle>();
		putDownParticles(swarm,F);
		Random r = new Random();
		
		for (int i=0; i<iteration; i++){
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
				if (F.f(x)<F.f(p.getLocalBest())){
					p.updateBest();
					if (F.f(x)<F.f(globalBest))
						globalBest = x.copy();
				}	
			}
		}
		
		System.out.println("Particle swarm optimalization is done with " + swarmSize + " particle and " + iteration + " iteration.");
		//System.out.println("((  Gradient here: " + F.Df(globalBest).toString() + "  ))");
		return globalBest;
	}

}
