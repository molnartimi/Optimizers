package other;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileWriter {

	private static ArrayList<ArrayList<Double>> PSOresults = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> PSOMresults = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> BEESresults = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> SIMresults = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> BFGSresults = new ArrayList<ArrayList<Double>>();
	private static int numberOfAlgorithm = 5;
	
	public static void writeOut(){
		FileWriter fileWriter = null;
		
		ArrayList<List<Double>> avarages = countAvarages();
		int maxRow = selectMaxRow(avarages);
		
		try {
			fileWriter = new FileWriter("teszteredmenyek.csv");
			
	/*		for(Integer i=0; i<numberOfAlgorithm; i++){
				String line = avarages.get(i).get(0).toString();
				for(Integer j=1; j<avarages.get(i).size(); j++){
					line += "," + avarages.get(i).get(j).toString();
				}
				fileWriter.append(line + "\n");
			}	*/
			
			fileWriter.append("Iteration,PSO,PSOM,Bees,SimAnn,LBFGS\n");
			
			for(Integer i=0; i<maxRow; i++){
				String line = ((Integer)(i+1)).toString();
				for(Integer j=0; j<numberOfAlgorithm; j++){
					if(avarages.get(j).size()>i)
						line += "," + avarages.get(j).get(i).toString();
					else
						line += ",";
				}
				fileWriter.append(line + "\n");
			}  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static int selectMaxRow(ArrayList<List<Double>> avarages) {
		int max = 0;
		for(int i=0; i<avarages.size(); i++){
			if(avarages.get(i).size()>max)
				max = avarages.get(i).size();
		}
		return max;
	}

	private static ArrayList<List<Double>> countAvarages() {
		ArrayList<List<Double>> avarages = new ArrayList<List<Double>>();
		
		avarages.add(Arrays.asList(countOneAvarage(PSOresults)));
		avarages.add(Arrays.asList(countOneAvarage(PSOMresults)));
		avarages.add(Arrays.asList(countOneAvarage(BEESresults)));
		avarages.add(Arrays.asList(countOneAvarage(SIMresults)));
		avarages.add(Arrays.asList(countOneAvarage(BFGSresults)));
		
		return avarages;
	}
	
	private static Double[] countOneAvarage(ArrayList<ArrayList<Double>> results){
		Double[] avarage = new Double[results.get(0).size()];
		
		for(int i=0; i<avarage.length; i++){
			avarage[i] = 0.0;
		}
		
		for (int i=0; i<results.size(); i++){
			for(int j=0; j<avarage.length; j++){
				if(results.get(i).size()>j)
					avarage[j] += results.get(i).get(j);
			}	
		}
		
		for (int i=0; i<avarage.length; i++){
			avarage[i] /= results.size();
		}
		
		return avarage;
	}
	
	public static void addPSOList(ArrayList<Double> list){
		PSOresults.add(list);
	}

	public static void addPSOMList(ArrayList<Double> list) {
		PSOMresults.add(list);
	}
	
	public static void addBeesList(ArrayList<Double> list) {
		BEESresults.add(list);
	}
	
	public static void addSimList(ArrayList<Double> list) {
		SIMresults.add(list);
	}
	
	public static void addBfgsList(ArrayList<Double> list) {
		BFGSresults.add(list);
	}
	
	
}
