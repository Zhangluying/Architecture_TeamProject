package part1;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Alina
 */
public class Load {
	Map<Integer,String> ROMMap = new HashMap<>();
    String fileName = "ROM.txt";
    
    public void readFile(int indexLine){
        File file = new File(fileName);
	    
	    
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = indexLine;
            while ((tempString = reader.readLine()) != null){
                line++;
                setValue(line, tempString);
            }
            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void setValue(int key, String value){
        ROMMap.put(key, value);
    }

    public String getValue(int key){
        return ROMMap.get(key);
    }

    public void clear(int key){ //clear one cell of memory
        ROMMap.put(key, "");
    }
}
