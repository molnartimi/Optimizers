import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.math3.linear.RealVector;

public class Main {
	public static double epszilon=0.001;
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		while(true){
		
		System.out.println("1 Newton's method\n2 Gradient descent\n3 Particle Swarm optimalization\n4 Simulated annealing");
		System.out.print("Select algorithm: ");
		int algId = sc.nextInt();
		
		Optimizer opt = new NewtonsMethod();
		
		switch (algId){
		case 1: break;
		case 2: opt = new GradientAlgorithm(); break;
		case 3: opt = new ParticleSwarmOptimalization(); break;
		case 4: opt = new SimulatedAnnealing(); break;
		}
		
		
		
		System.out.println("\n1 y = (x-3)^2\n2 y = (x-21)^2+5x\n3 y = sin(x)+cos(y)\n4 z = x^2+y^2-x\n5 k = x^2+y^2+(z-1)^2\n6 y = sin(x)");
		System.out.print("Select testfunction: ");
		int funcId = sc.nextInt();
		System.out.println();
		
		Function f = new ExampleFunction(funcId);
		
		switch (funcId){
		case 3:; case 4:; case 5:   RealVector pointV = opt.Method(f);
									System.out.println("\nStationary point: " + pointV.toString());
									break;
		default:	double pointD = opt.method(f);
					System.out.println("Stationary point: " + pointD);
					break;
		}
		
		
		}
		
		
	}
}
