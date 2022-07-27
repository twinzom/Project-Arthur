package project.arthur.util.dataloader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class YahooStockFetcher {

	/*
	 * Fetch stock information from Yahoo
	 * https://query2.finance.yahoo.com/v1/finance/screener?crumb=DtLJupaiDtJ&lang=en-US&region=US&formatted=true&corsDomain=finance.yahoo.com
	 * It will only load static stock information
	 * 
	 */
    
	public static void main(String[] args) throws Exception {
		
	    String URL = "https://query2.finance.yahoo.com/v1/finance/screener?crumb=DtLJupaiDtJ&lang=en-US&region=US&formatted=true&corsDomain=finance.yahoo.com";
	    String OFFSET = "${OFFSET}";
	    String REQ_BODY = "{\"size\":250,\"offset\":"+OFFSET+",\"sortField\":\"ticker\",\"sortType\":\"asc\",\"quoteType\":\"EQUITY\",\"topOperator\":\"AND\",\"query\":{\"operator\":\"AND\",\"operands\":[{\"operator\":\"or\",\"operands\":[{\"operator\":\"EQ\",\"operands\":[\"exchange\",\"NYQ\"]},{\"operator\":\"EQ\",\"operands\":[\"exchange\",\"NGM\"]},{\"operator\":\"EQ\",\"operands\":[\"exchange\",\"NCM\"]},{\"operator\":\"EQ\",\"operands\":[\"exchange\",\"NAS\"]},{\"operator\":\"EQ\",\"operands\":[\"exchange\",\"NMS\"]}]}]},\"userId\":\"\",\"userIdType\":\"guid\"}";
	    String COOKIE = "B=fbsd93lgpetbn&b=3&s=le; A1=d=AQABBHd1l2ECEJvBwMhwceVeOfzJ9x2p8fUFEgEBBgHxK2IRY1iia3sB_eMBAAcId3WXYR2p8fU&S=AQAAAhUO3dPiYT1kbL5L_dYf39c; A3=d=AQABBHd1l2ECEJvBwMhwceVeOfzJ9x2p8fUFEgEBBgHxK2IRY1iia3sB_eMBAAcId3WXYR2p8fU&S=AQAAAhUO3dPiYT1kbL5L_dYf39c; GUC=AQEBBgFiK_FjEUIh9QT4; A1S=d=AQABBHd1l2ECEJvBwMhwceVeOfzJ9x2p8fUFEgEBBgHxK2IRY1iia3sB_eMBAAcId3WXYR2p8fU&S=AQAAAhUO3dPiYT1kbL5L_dYf39c&j=WORLD; PRF=t%3DMSFT%252BBAC.MX%252BAKO-A%252BKO%252BAPI; cmp=t=1658898569&j=0&u=1---";
	    String OUTPUT_PATH = System.getProperty("user.home")+"Projects/git/Project-Arthur/dataloader/datafile/yahoo/";
	    
	    for(int i=0;i<40;i++) {
	    	
	    	String reqBody = REQ_BODY.replace(OFFSET, ""+(i*250));
	    	
	    	HttpRequest request = HttpRequest.newBuilder()
					  .uri(new URI(URL))
					  .headers("Content-Type", "application/json", "cookie", COOKIE)
					  .POST(BodyPublishers.ofString(reqBody))
					  .build();
	    	
	    	HttpResponse<String> response = HttpClient
					  .newBuilder()
					  .build()
					  .send(request, BodyHandlers.ofString());
			
			String respText = response.body();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH+"yahoo_stocks_"+i+".json"));
		    writer.write(respText);
		    writer.close();
			
	    }
	   
	}
}
