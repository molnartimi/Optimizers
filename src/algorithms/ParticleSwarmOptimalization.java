package algorithms;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import functions.Function;
import main.Main;

public class ParticleSwarmOptimalization implements Optimizer {
	protected int swarmSize = 30;
	protected double border = 5;
	protected int iteration = 10;
	protected RealVector globalBest = null;
	protected double globalBestF;
	
	protected double omega = 0.2;
	protected double fiP = 0.4;
	protected double fiG = 0.8;
	
	protected class Particle {
		private RealVector x;
		private RealVector v;
		private RealVector best;
		private double bestValue;
		private Function F;
		
		public Particle (Function f, RealVector pos, RealVector vel){
			F = f;
			x = pos;
			best = x.copy();
			v = vel;
			bestValue = F.f(best);
		}
		
		public void updateBest() {
			best = x.copy();
			bestValue = F.f(best);
		}
		
		public RealVector getLocalBest() {
			return best;
		}
		
		public double getLocalBestValue(){
			return bestValue;
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
		
		for(int i=0; i<swarmSize; i++) {
			RealVector pos = MatrixUtils.createRealVector(new double[F.getDimension()]);
			RealVector vel = MatrixUtils.createRealVector(new double[F.getDimension()]);
			for (int d=0; d<F.getDimension(); d++){
				pos.setEntry(d, r.nextDouble()*border);
				vel.setEntry(d, r.nextDouble()*2*border);
			}
			
			Particle p = new Particle(F,pos,vel);
			swarm.add(p);
			
			if ( globalBest == null || p.getLocalBestValue()<globalBestF){
				globalBest = pos.copy();
				globalBestF = F.f(globalBest);
			}
			
		}
	}
	
	@Override
	public double method(Function f) {
		return 0;
	}

	@Override
	public RealVector Method(Function F) {
		System.out.println("Particle swarm optimalization is started");
		
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
				double Ff = F.f(x);
				if (Ff < p.getLocalBestValue()){
					p.updateBest();
					if (Ff < globalBestF){
						globalBest = x.copy();
						globalBestF = F.f(globalBest);
					}
				}	
			}
		}
		
		System.out.println("Particle swarm optimalization is done with " + swarmSize + " particle and " + iteration + " iteration.");
		//System.out.println("((  Gradient here: " + F.Df(globalBest).toString() + "  ))");
		return globalBest;
	}

}
