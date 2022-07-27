package project.arthur.util.dataloader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class YahooHistoricalQuoteFetcher {

	/*
	 * Fetch historical quote from Yahoo
	 * https://query1.finance.yahoo.com/v7/finance/download/KO?period1=-252374400&period2=1658880000&interval=1d&events=history&includeAdjustedClose=true
	 * It will only load static stock information
	 * 
	 */
    
	public static void main(String[] args) throws Exception {
		
		String SYMBOL = "${SYMBOL}";
	    String URL = "https://query1.finance.yahoo.com/v7/finance/download/"+SYMBOL+"?period1=-252374400&period2=1658880000&interval=1d&events=history&includeAdjustedClose=true";
	    String COOKIE = "B=fbsd93lgpetbn&b=3&s=le; A1=d=AQABBHd1l2ECEJvBwMhwceVeOfzJ9x2p8fUFEgEBBgHxK2IRY1iia3sB_eMBAAcId3WXYR2p8fU&S=AQAAAhUO3dPiYT1kbL5L_dYf39c; A3=d=AQABBHd1l2ECEJvBwMhwceVeOfzJ9x2p8fUFEgEBBgHxK2IRY1iia3sB_eMBAAcId3WXYR2p8fU&S=AQAAAhUO3dPiYT1kbL5L_dYf39c; GUC=AQEBBgFiK_FjEUIh9QT4; A1S=d=AQABBHd1l2ECEJvBwMhwceVeOfzJ9x2p8fUFEgEBBgHxK2IRY1iia3sB_eMBAAcId3WXYR2p8fU&S=AQAAAhUO3dPiYT1kbL5L_dYf39c&j=WORLD; PRF=t%3DMSFT%252BBAC.MX%252BAKO-A%252BKO%252BAPI; cmp=t=1658898569&j=0&u=1---";
	    String OUTPUT_PATH = System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/yahoo/";
	    String INPUT_FILE = System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/yahoo/yahoo_stocks_list.txt";
	    
	    
	    BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE));
	    
	    String symbol;
		
	    while ((symbol = reader.readLine()) != null) {
	    	
	    	File file = new File(OUTPUT_PATH+symbol+".csv");
	    	
	    	if (!file.exists()) {
			
		    	System.out.print("Download "+symbol+"...");
		    	
				String url = URL.replace(SYMBOL, symbol);
		    	
		    	HttpRequest request = HttpRequest.newBuilder()
						  .uri(new URI(url))
						  .headers("cookie", COOKIE, "accept-encoding", "gzip")
						  .GET()
						  .build();
		    	
		    	HttpResponse<String> response = HttpClient
						  .newBuilder()
						  .build()
						  .send(request, BodyHandlers.ofString());
				
				String respText = response.body();
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH+symbol+".csv"));
			    writer.write(respText);
			    writer.close(); 
				
			    System.out.println("Completed!");
	    	
	    	}
		}
	   
	}
}
