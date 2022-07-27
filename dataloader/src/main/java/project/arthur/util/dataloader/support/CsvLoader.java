package project.arthur.util.dataloader.support;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvLoader {

    public List<String[]> oneByOne(String filePath) throws Exception {
        return oneByOne(filePath, 0);
    }
    
    public List<String[]> oneByOne(String filePath, int skipLine) throws Exception {
        
        Reader reader = new FileReader(filePath);
        
        List<String[]> list = new ArrayList<String[]>();
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        int lineCount=0;
        while ((line = csvReader.readNext()) != null) {
        	if (lineCount>=skipLine)
        		list.add(line);
        	lineCount++;
        }
        reader.close();
        csvReader.close();
        return list;
    }
    
}
