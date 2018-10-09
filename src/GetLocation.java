import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetLocation {
	double[][] location = new double[16][4];

	public double[][] readArea() throws FileNotFoundException, IOException, ParseException{
        JSONParser parser = new JSONParser();				
        Object ob = parser.parse(new FileReader("src/melbGrid.json"));	       
        JSONObject dataJson =(JSONObject) ob;
		JSONArray features =(JSONArray) dataJson.get("features");
		Iterator i = features.iterator();
		int row = 0;
       while(i.hasNext()) {  
		    JSONObject featuresinfo = (JSONObject) i.next();
		    JSONObject properties = (JSONObject) featuresinfo.get("properties");
		    double xmin = (double) properties.get("xmin");
		    double xmax = (double) properties.get("xmax");
		    double ymin = (double) properties.get("ymin");
		    double ymax = (double) properties.get("ymax");
		    this.location[row][0] = xmin;
		    this.location[row][1] = xmax;
		    this.location[row][2] = ymin;
		    this.location[row][3] = ymax;
		    row++;
		} 
       return this.location;
	}
	public int getrow(double b){
		for(int i=0;i<16;i++){
			if(this.location[i][0] == b){
				return i;
			}
		}
		return -1;
	}
	
}
