package project.arthur.util.dataloader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import project.arthur.util.dataloader.support.CsvLoader;

public class NqasdaqHistoricalQuoteDownloader {
	public static void main(String[] args) throws Exception {

		String[] filePaths = new String[] {
				System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/nasdaq_screener_nyse.csv",
				System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/nasdaq_screener_nasdaq.csv" };

		String OUTPUT_PATH = System.getProperty("user.home")+"/Projects/git/Project-Arthur/dataloader/datafile/output/";
		
		String SYMBOL = "${Symbol}";

		String URL = "https://api.nasdaq.com/api/quote/" + SYMBOL
				+ "/historical?assetclass=stocks&fromdate=2012-07-26&limit=9999&todate=2022-07-26";

		for (int i = 0; i < filePaths.length; i++) {

			String filePath = filePaths[i];

			CsvLoader csvLoader = new CsvLoader();

			List<String[]> rows = csvLoader.oneByOne(filePath, 1);

			AtomicInteger counter = new AtomicInteger();
			
			rows.forEach(row -> {

				System.out.print("Going to download: "+row[0]+"...");
				
				String url="";
				try {
					url = URL.replace(SYMBOL, URLEncoder.encode(row[0].replace("/", "%sl%"), StandardCharsets.UTF_8.toString()));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				byte[] sampleData = "Sample request body".getBytes();
				
				
				try {
					HttpRequest request = HttpRequest.newBuilder()
					  .uri(new URI(url))
					  //.headers("Content-Type", "text/plain;charset=UTF-8")
					  .GET()
					  .build();
					
					HttpResponse<String> response = HttpClient
							  .newBuilder()
							  .build()
							  .send(request, BodyHandlers.ofString());
					
					String respText = response.body();
					
					//System.out.println(respText);
					
					BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH+(row[0].replace("/", "-"))+".json"));
				    writer.write(respText);
				    writer.close();
					
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Completed! "+(counter.getAndIncrement()));

			});

		}
	}

}
