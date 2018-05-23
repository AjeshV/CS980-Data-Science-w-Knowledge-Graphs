package edu.unh.cs.datascience;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class PageRank {

    public static void main(String[] args) throws FileNotFoundException {
    	Scanner scanner = new Scanner(new FileReader("C:\\Users\\Ajesh\\Desktop\\UNH\\Courses\\CS 980 Imp int (Data Science)\\A3 - Graph walks - PageRank\\graph.txt"));
            

    		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

    		Map<Integer,Double> map = new HashMap<Integer,Double>();
              
    		int edges = 0;
            while (scanner.hasNextLine()) {
            	String[] columns = scanner.nextLine().split("\\s+");
            	ArrayList<String> list = new ArrayList<String>();
            	for (int i = 1; i < columns.length; i++) {
            		list.add(columns[i]);
            		edges += 1;
            	}
            	
            	hashMap.put(columns[0], list);         
            }
            


            int nodes = hashMap.size();
            double rjf = 0.15;
            
           	//2nd hashmap
            int i = 0;
            int k = 0;

            ArrayList<Integer> value1_1 = null;
            double[] value2 = null;
            int l = 0;
            Set entries = hashMap.entrySet();
            Iterator entriesIterator = entries.iterator();
            
            while(entriesIterator.hasNext()){

            	Map.Entry mapping = (Map.Entry) entriesIterator.next();
            	k = Integer.parseInt((String) mapping.getKey());

            	map.put(k, (double)1/nodes);

            	            	                    	
            	i++;
            }

            
            System.out.println(map);

            

            List<String> value2_1 = null;
            List<Double> value3 =  new ArrayList<Double>();
            for(Entry<Integer, Double> entry1: map.entrySet()) {
            	   Integer key1 = entry1.getKey();
            	   for(HashMap.Entry<String, List<String>> entry2: hashMap.entrySet()) {
            	       String key2 = entry2.getKey();
            	       if (key1 == Integer.parseInt(key2)) {
            	    	   value2_1 = entry2.getValue();
                	       System.out.println(value2_1);
	            	       
                	       int count = 0;
            	    	   if (!value2_1.isEmpty()) {
		            	       for (String item : value2_1){
			            	    	   for(Entry<Integer, Double> entry4: map.entrySet()) { 	            	    		   
			            	    		   Integer key4 = entry4.getKey();
			 	            	           if (item.equals(key4.toString())){
			 	            	        	   value3.add(entry4.getValue());
			 	            	        	   count++;
				            	          }
			            	    	   }
		            	    	   }
	            	       }
	            	       System.out.println(value3);
            	       }
            	   }
            	}
            
            
     /*       
            String key = null;
            List<String> values;
            for (HashMap.Entry<String, List<String>> entry : hashMap.entrySet()) {
                key = entry.getKey();
                values = entry.getValue();
                //map.put(k, (double)1/nodes);
                System.out.println("Key = " + key);
                System.out.println("Values = " + values);
            }
      */      
            //for (int j = 0; j < 2; j++) {
            	/*List<String> value1 = new ArrayList<String>();
            	
            	value1 = hashMap.get(k);
            	System.out.println(value1);            	           	
            	//map.put(k, value4);
            */
            //}

            
            System.out.println("Number of components:  1");
            System.out.println("Number of edges: " + edges);
            System.out.println("Number of nodes: "+ nodes);
            System.out.println("Random jump factor: " + rjf);
            
       }
}  
