package project.arthur.util.dataloader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

public class YahooStockDetailExtractor {

	/*
	 * Extract stock detail from pre-downloaded yahoo stock information
	 * It will only load static stock information
	 * 
	 */
    
	public static void main(String[] args) throws Exception {
		
		String FILE_INDEX = "${index}";
		String INPUT_FILE = System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/yahoo/yahoo_stocks_"+FILE_INDEX+".json";
		String OUTPUT_FILE = System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/yahoo/yahoo_stocks_detail.txt";
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE));
		
		for (int i=0;i<=35;i++) {
			Path filePath = Path.of(INPUT_FILE.replace(FILE_INDEX, ""+i));
			String jsonText = Files.readString(filePath);
			JSONObject json = new JSONObject(jsonText);
			JSONArray qoutes = json.getJSONObject("finance")
					.getJSONArray("result")
					.getJSONObject(0)
					.getJSONArray("quotes");
			for (int j=0; j < qoutes.length(); j++) {
			    JSONObject qoute = qoutes.getJSONObject(j);
			    writer.write(qoute.getString("symbol"));
			    writer.newLine();
			    System.out.println(qoute.getString("symbol"));
			}
		}
		
		writer.close();
	}
	
	
}
