package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter {

	private static char delimiter = ',';
	private static String newline = "\n";
	
	public static void writeOut(String fileName, List<Double> results){
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append("Iteration,FunctionValue");
			fileWriter.append(newline);
			
			for(Integer i=0; i<results.size(); i++){
				fileWriter.append(i.toString() + delimiter + results.get(i).toString() + newline);
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
	
	
}
