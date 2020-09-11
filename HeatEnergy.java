import java.util.Scanner;

public class HeatEnergy
{
	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Enter the mass of the water, in grams.");
		double mass = reader.nextDouble();
		System.out.println("Enter the initial temperature, in Celcius.");
		double initialTemp = reader.nextDouble();
			if(initialTemp < -273.14)
				initialTemp = -273.14;
		System.out.println("Enter the final temperature, in Celcius.");
		double finalTemp = reader.nextDouble();
			if(finalTemp < -273.14)
				finalTemp = -273.14;
		
		String initialPhase = "Liquid";
		if(initialTemp < 0)
			initialPhase = "Solid";
		if(initialTemp > 100)
			initialPhase = "Vapor";
		
		String finalPhase = "Liquid";
		if(finalTemp < 0)
			finalPhase = "Solid";
		if(finalTemp > 100)
			finalPhase = "Vapor";
		
		System.out.println("\nMass: " + mass + "g");
		System.out.println("Initial Temperature: " + initialTemp + "C " + initialPhase);
		System.out.println("Final Temperature: " + finalTemp + "C " + finalPhase + "\n");
		
		boolean endothermic = false;
		if(finalTemp > initialTemp)
			endothermic = true;
		
		double heatEnergy = 0;
		
		if(initialPhase.equals("Solid")) {
			heatEnergy += tempChangeSolid(mass, initialTemp, finalTemp, finalPhase, endothermic);
			
			if(!finalPhase.equals("Solid")) {
				heatEnergy += fusion(mass, endothermic);
				heatEnergy += tempChangeLiquid(mass, 0, finalTemp, finalPhase, endothermic);
			}
			
			if(finalPhase.equals("Vapor")) {
				heatEnergy += vaporization(mass, endothermic);
				heatEnergy += tempChangeVapor(mass, 100, finalTemp, finalPhase, endothermic);
			}
		}
		
		if(initialPhase.equals("Liquid")) {
			heatEnergy += tempChangeLiquid(mass, initialTemp, finalTemp, finalPhase, endothermic);
			
			if(finalPhase.equals("Solid")) {
				heatEnergy += fusion(mass, endothermic);
				heatEnergy += tempChangeSolid(mass, 0, finalTemp, finalPhase, endothermic);
			}
		
			if(finalPhase.equals("Vapor")) {
				heatEnergy += vaporization(mass, endothermic);
				heatEnergy += tempChangeVapor(mass, 100, finalTemp, finalPhase, endothermic);
			}
		}
		
		if(initialPhase.equals("Vapor")) {
			heatEnergy += tempChangeVapor(mass, initialTemp, finalTemp, finalPhase, endothermic);
			
			if(!finalPhase.equals("Vapor")) {
				heatEnergy += vaporization(mass, endothermic);
				heatEnergy += tempChangeLiquid(mass, 100, finalTemp, finalPhase, endothermic);
			}
			
			if(finalPhase.equals("Solid")) {
				heatEnergy += fusion(mass, endothermic);
				heatEnergy += tempChangeSolid(mass, 0, finalTemp, finalPhase, endothermic);
			}
		}
			
		System.out.println("Total Heat Energy: " + heatEnergy + "kJ");
	}
	
	public static double tempChangeSolid(double mass, double startTemp, double endTemp, String endPhase, boolean endothermic) {
		if(!endPhase.equals("Solid"))
			endTemp = 0;
		double energyChange = round(mass*0.002108*(endTemp - startTemp));
		if(endothermic)
			System.out.println("Heating (solid): " + energyChange + "kJ");
		else
			System.out.println("Cooling (solid): " + energyChange + "kJ");
		return energyChange;
	}
	
	public static double tempChangeLiquid(double mass, double startTemp, double endTemp, String endPhase, boolean endothermic) {
		if(endPhase.equals("Solid"))
			endTemp = 0;
		if(endPhase.equals("Vapor"))
			endTemp = 100;
		double energyChange = round(mass*0.004184*(endTemp - startTemp));
		if(endothermic)
			System.out.println("Heating (liquid): " + energyChange + "kJ");
		else
			System.out.println("Cooling (liquid): " + energyChange + "kJ");
		return energyChange;
	}
	
	public static double tempChangeVapor(double mass, double startTemp, double endTemp, String endPhase, boolean endothermic) {
		if(!endPhase.equals("Vapor"))
			endTemp = 100;
		double energyChange = round(mass*0.001996*(endTemp - startTemp));
		if(endothermic)
			System.out.println("Heating (vapor): " + energyChange + "kJ");
		else
			System.out.println("Cooling (vapor): " + energyChange + "kJ");
		return energyChange;
	}
	
	public static double fusion(double mass, boolean endothermic) {
		double energyChange; 
		if(endothermic) {
			energyChange = round(0.333*mass);
			System.out.println("Melting: " + energyChange + "kJ");
		}
		else {
			energyChange = round(-0.333*mass);
			System.out.println("Freezing: " + energyChange + "kJ");
		}
		return energyChange;
	}
	
	public static double vaporization(double mass, boolean endothermic) {
		double energyChange; 
		if(endothermic) {
			energyChange = round(2.257*mass);
			System.out.println("Evaporation: " + energyChange + "kJ");
		}
		else {
			energyChange = round(-2.257*mass);
			System.out.println("Condensation: " + energyChange + "kJ");
		}
		return energyChange;
	}
	
	public static double round(double x) {
		x *= 1000;
		if(x > 0)
			return (int)(x + 0.5)/1000.0;
		else
			return (int)(x - 0.5)/1000.0;
	}
}
	