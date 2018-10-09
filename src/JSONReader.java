import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import mpi.MPI;

public class JSONReader {
	
	HashMap<Integer,String> hm = new HashMap<Integer,String>();
	static int user = 0;
	public double[][] readArea() throws FileNotFoundException, IOException{
		double[][] location = new double[16][4];
		int row = 0;
		InputStream fis1 = new FileInputStream("src/melbGrid.json");
        JsonReader reader1 = new JsonReader(new InputStreamReader(fis1, "UTF-8"));
        reader1.beginObject();
        
        while(reader1.hasNext()) {
        	
        	String s1 = reader1.nextName();
        	if(s1.equals("features")){
        		reader1.beginArray();
        		while(reader1.hasNext()) {
        			reader1.beginObject();
        			while(reader1.hasNext()) {
        				String s2 = reader1.nextName();
        				if(s2.equals("properties")){
        					 reader1.beginObject();
        					 
        					 while(reader1.hasNext()) {
        						      						 
        						 String s3 = reader1.nextName();
        						 if(s3.equals("id")){
        							 reader1.skipValue();
        							 
        						 }  
        						 s3 = reader1.nextName();
        						 if(s3.equals("xmin")){
        							 double xmin = Double.parseDouble(reader1.nextString());
        							 location[row][0] = xmin;
        							 
        						 }
        						 s3 = reader1.nextName();
        						 if(s3.equals("xmax")){
        							 double xmax = Double.parseDouble(reader1.nextString());
        							 location[row][1] = xmax;
        							 
        						 }
        						 s3 = reader1.nextName();
        						 if(s3.equals("ymin")){
        							 double ymin = Double.parseDouble(reader1.nextString());
        							 location[row][2] = ymin;
        							
        						 }
        						 s3 = reader1.nextName();
        						 if(s3.equals("ymax")){
        							 double ymax = Double.parseDouble(reader1.nextString());
        							 location[row][3] = ymax;
        						 }			 
        						 row++;
            					 
        					 }       					
        					 reader1.endObject();
        				}else{
        					reader1.skipValue();
        				}
        			}
        			reader1.endObject();
        		}
        		reader1.endArray();
        	}else{
        		reader1.skipValue();
        	}		    
		}
       reader1.endObject();
       return location;
	}
	public int[] readStream(JsonReader reader,String[] args,double[][] location)throws FileNotFoundException, IOException {
		 int[] count = new int[16];
		 for(int m=0;m<count.length;m++){
			 count[m]=0;
		 }
	    try {

	        	reader.beginObject();        	
	        	while(reader.hasNext()){
	        		int total = 0;
	        		String s1 = reader.nextName();
	        		if(s1.equals("meta")){
	        			reader.skipValue();
	        		}        		
	        		String s2 = reader.nextName();
	        		if(s2.equals("json")){
	        			reader.beginObject(); 
	        			while(reader.hasNext()){
	        				String s22 = reader.nextName();
	        				if(!s22.equals("geo")){
	        					reader.skipValue();
	        				}else if(s22.equals("geo")){
	        					reader.beginObject();
	        					while(reader.hasNext()){
	        						String s222 = reader.nextName();
	        						if(!s222.equals("coordinates")){        							
	        							reader.skipValue();
	        						}else if(s222.equals("coordinates")){
	        							reader.beginArray();
	        							while  (reader.hasNext()) {
	        								  double latitude = Double.parseDouble(reader.nextString());
	        								  double longitude = Double.parseDouble(reader.nextString());
	        								  for(int d=0;d<count.length;d++){
	        										 if(location[d][0]<longitude && longitude<location[d][1] && location[d][2]<latitude && latitude<location[d][3]){
	        											 count[d] = count[d]+1;
	        											 break;
	        										 }
	        									 }
	        		                          total++;
	        		                     }
	        							reader.endArray();
	        						}	        						
	        					}
	        					reader.endObject();
	        				}        					        				
	        			}        			
	        			reader.endObject();
	        		}
	        	}
	        	reader.endObject();	    
	    } catch (IOException ex) {
      
	    }
		 return count;
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
	public void rankByNumber(int[] count1){
		System.out.println("Rank by total number of tweets in each box");
	    int max = -1;
	    int index = -1;
	    int total = 0;
	    int[] count = new int[count1.length];
	    for(int i=0;i<count.length;i++){
	    	count[i] = count1[i];
	    }
	    for(int i=0;i<count.length;i++){
	    	total = total + count[i];
	    }
	    for(int j=0;j<count.length;j++){	    	
	    	for(int i=0;i<count.length;i++){
	    		if(count[i]>max){
	    			max = count[i];
	    			index = i;    				    		
	    			}
	    	}
	    	System.out.println(hm.get(index)+": "+max);
	    	count[index] = -1;
	    	max = -1;    	
	    }
	    System.out.println("total number: "+total);
	    System.out.println(" ");
	}
	public void rankByRow(int[] count){
		System.out.println("Rank by row");
	    int aRow = count[0]+count[1]+count[2]+count[3];
	    int bRow = count[4]+count[5]+count[6]+count[7];
	    int cRow = count[8]+count[9]+count[10]+count[11]+count[12];
	    int dRow = count[13]+count[14]+count[15]; 
	    int total = aRow+bRow+cRow+dRow;
	    for(int i=0;i<4;i++){
	    	int max = Math.max(aRow,bRow);
	    	   max = Math.max(max,cRow);
	    	   max = Math.max(max,dRow);
	    	   if(max == aRow){
	    		   System.out.println("A-ROW: "+max);
	    	       aRow = -1;
	    	   }else if(max == bRow){
	    	       System.out.println("B-ROW: "+max);
	    	       bRow = -1;
	    	   }else if(max == cRow){
	    	       System.out.println("C-ROW: "+max);
	    	       cRow = -1;
	    	   }else if(max == dRow){
	    	       System.out.println("D-ROW: "+max);
	    	       dRow = -1;
	    	   }
	    } 
	    System.out.println("total number: "+total);
	    System.out.println(" ");
	}
	public void rankByColumn(int[] count){
		System.out.println("Rank by column");
		int column1 = count[0]+count[4]+count[8];
	    int column2 = count[1]+count[5]+count[9];
	    int column3 = count[2]+count[6]+count[10]+count[13];
	    int column4 = count[3]+count[7]+count[11]+count[14]; 
	    int column5 = count[12]+count[15];
	    int total = column5+column4+column3+column2+column1;
	    for(int i=0;i<5;i++){
	    	int max = Math.max(column1,column2);
	    	   max = Math.max(max,column3);
	    	   max = Math.max(max,column4);
	    	   max = Math.max(max,column5);
	    	   if(max == column1){
	    		   System.out.println("column1: "+max);
	    		   column1 = -1;
	    	   }else if(max == column2){
	    	       System.out.println("column2: "+max);
	    	       column2 = -1;
	    	   }else if(max == column3){
	    	       System.out.println("column3: "+max);
	    	       column3 = -1;
	    	   }else if(max == column4){
	    	       System.out.println("column4: "+max);
	    	       column4 = -1;
	    	   }else if(max == column5){
	    	       System.out.println("column5: "+max);
	    	       column5 = -1;
	    	   }
	    } 
	    System.out.println("total number: "+total);
	    System.out.println(" ");
	}
	public static void main(String[] args) throws IOException{
 		MPI.Init(args);
	    int me=MPI.COMM_WORLD.Rank();
		int size=MPI.COMM_WORLD.Size();
		JSONReader jr = new JSONReader();
		jr.setHashMap();
		double[][] location = jr.readArea();
		long startTime=System.currentTimeMillis();   

		
		InputStream fis = new FileInputStream("src/tinyTwitter.json");
        JsonReader reader = new JsonReader(new InputStreamReader(fis, "UTF-8"));
        reader.setLenient(true);
        int[] counttemp = new int[16];
        int[] receivebuffer = new int[16];
        for(int i=0;i<counttemp.length;i++){  
    		counttemp[i] = 0;
    		receivebuffer[i] = 0;
		}
 		reader.beginArray();

        while (reader.hasNext()) {
        	user++;
      		if(user%size==me){    			     
      			int[] counttemp11 = new int[16];
      		  JsonToken check4 = reader.peek();
				if (check4 == JsonToken.NULL) {	        	        		        	
     			 	reader.skipValue();  			   
     		 	}else{
     		 		counttemp11 = jr.readStream(reader,args,location);
     		 		for(int i=0;i<counttemp.length;i++){
     		 			counttemp[i] = counttemp[i]+counttemp11[i];
     		 		}   
     		 	}
      		}else{
      			reader.skipValue();
      		}
  
        }
       
        reader.endArray();      
        reader.close();
        MPI.COMM_WORLD.Reduce(counttemp, 0, receivebuffer, 0,receivebuffer.length, MPI.INT, MPI.SUM, 0);
        
        if(me == 0){
        	
        	jr.rankByNumber(receivebuffer);
        	jr.rankByColumn(receivebuffer);
        	jr.rankByRow(receivebuffer);
        	long endTime=System.currentTimeMillis(); 
        	System.out.println("running time: "+ (endTime-startTime)/1000+" sceonds");
        	System.out.println("process: "+size);
        }
    	MPI.Finalize();
	}
}
