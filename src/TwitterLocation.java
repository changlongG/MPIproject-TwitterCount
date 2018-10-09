
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
public class TwitterLocation {
	HashMap<Integer,String> hm = new HashMap<Integer,String>();
	public void readTWLocation() throws FileNotFoundException, IOException, ParseException{
		 GetLocation test = new GetLocation();
		 double[][] location = test.readArea();
		 JSONParser parser = new JSONParser();				
         Object ob = parser.parse(new FileReader("src/cloud/tinyTwitter.json"));	       
		 JSONArray jsoninfo =(JSONArray) ob;
		 Iterator i = jsoninfo.iterator();
		 int total = 0;
		 int active = 0;
		 int[] count = new int[16];
		 for(int m=0;m<count.length;m++){
			 count[m]=0;
		 }
		 while(i.hasNext()){
			 JSONObject user = (JSONObject) i.next();
			 JSONObject json = (JSONObject) user.get("json");
			 JSONObject geo = (JSONObject) json.get("geo");
			 JSONArray coordinates = (JSONArray) geo.get("coordinates");
			 Iterator j = coordinates.iterator();
			 Object temp;
			 total++;
			 while(j.hasNext()){
				 
				 temp = j.next();
				 if(temp.getClass().toString().equals("class java.lang.Long")){
					 continue;
				 }	
				 active++;
				 double latitude = (double) temp;
				 double longitude = (double) j.next();
				 for(int d=0;d<count.length;d++){
					 if(location[d][0]<longitude && longitude<location[d][1] && location[d][2]<latitude && latitude<location[d][3]){
						 count[d] = count[d]+1;
						 break;
					 }
				 }
			    /* System.out.println("latitude: "+latitude+" longitude: "+longitude);*/
			 }
		 }
		/*  */  
		 for(int p=0;p<count.length;p++){
			 String temp1 = hm.get(p);
			 //System.out.println(temp1);
	
			 System.out.println(temp1+": "+count[p]);
		 }
		 System.out.println("total: "+total + " active: "+active);
	}
	public void setHashMap(){
		this.hm.put(0,"A1");
		this.hm.put(1,"A2");
		this.hm.put(2,"A3");
		this.hm.put(3,"A4");
		this.hm.put(4,"B1");
		this.hm.put(5,"B2");
		this.hm.put(6,"B3");
		this.hm.put(7,"B4");
		this.hm.put(8,"C1");
		this.hm.put(9,"C2");
		this.hm.put(10,"C3");
		this.hm.put(11,"C4");
		this.hm.put(12,"C5");
		this.hm.put(13,"D3");
		this.hm.put(14,"D4");
		this.hm.put(15,"D5");		
	}
	public static void main(String args[]) throws  ParseException, FileNotFoundException, IOException{
		/*		 String regexPattern = "[aeiou]";
				 char a = 'a';
				 String c = "aa";
				 String b = String.valueOf(a);
				   Pattern pattern = Pattern.compile(regexPattern);
			        Matcher matcher = pattern.matcher(b);
				 if(matcher.matches()){
					 System.out.println("是否匹配：" + matcher.matches());
				 }
				 JSONObject dataJson */
		TwitterLocation tl = new TwitterLocation();
		tl.setHashMap();
		//tl.readArea();
		tl.readTWLocation();	
			       
	}
}
