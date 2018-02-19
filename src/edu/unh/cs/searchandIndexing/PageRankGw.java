package lucene.searchandIndexing;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class PageRank {

    public static void main(String[] args) throws FileNotFoundException {
    	Scanner scanner = new Scanner(new FileReader("C:\\Users\\Ajesh\\Desktop\\UNH\\Courses\\CS 980 Imp int (Data Science)\\A3 - Graph walks - PageRank\\graph.txt"));
            
    		//HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
    		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
    		// HashMap<Integer, ArrayList<Integer>> hashMap2 = new HashMap<Integer, ArrayList<Integer>>();
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
            
            //System.out.println(hashMap);

            int nodes = hashMap.size();
            double rjf = 0.15;
            
           	//2nd hashmap
            int i = 0;
            int k = 0;
            //List<Integer> value1 = new ArrayList<Integer>();
            //List<Integer> value2 = new ArrayList<Integer>();
            ArrayList<Integer> value1_1 = null;
            double[] value2 = null;
            int l = 0;
            Set entries = hashMap.entrySet();
            Iterator entriesIterator = entries.iterator();
            
            while(entriesIterator.hasNext()){

            	Map.Entry mapping = (Map.Entry) entriesIterator.next();
            	k = Integer.parseInt((String) mapping.getKey());
            	//value1 = (List<Integer>) mapping.getValue();
            	//value1_1 = (int[]) mapping.getValue();
            	map.put(k, (double)1/nodes);
            	//System.out.println(k);
            	//System.out.println(value1_1);
            	            	                    	
            	i++;
            }
        	//value1_1 = (ArrayList<Integer>) mapping.getValue();
            
            System.out.println(map);
            //System.out.println(value1_1);
            
            //2nd iteration
            //Integer[] value2_1 = null;
            List<String> value2_1 = null;
            List<Double> value3 =  new ArrayList<Double>();
            for(Entry<Integer, Double> entry1: map.entrySet()) {
            	   Integer key1 = entry1.getKey();
            	   //int hash1 = System.identityHashCode(key1);
            	   //Value value1 = entry1.getValue();
            	   //System.out.println(key1);
            	   for(HashMap.Entry<String, List<String>> entry2: hashMap.entrySet()) {
            	       String key2 = entry2.getKey();
            	       if (key1 == Integer.parseInt(key2)) {
            	    	   //value2_1 = entry2.getValue().toArray(value2_1);
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
            for (int j = 0; j < value1_1.size(); j++) {
        		if (value1_1.contains(k)) {
                	System.out.println("1");
        			//get value of key k;
        			value2[l] = map.get(k);
                    System.out.println(value2[l]);
        			l += 1;           			
        		}
        	}
            */
            
            
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
            	
            	//value1 = values of given key k from hashmap;
            	value1 = hashMap.get(k);
            	System.out.println(value1);            	           	
            	//value2 = values of value1 as keys from map;
            	//value2 = 
            	//value3 = (sum of number of values for each value1 as keys from hashmap);
            	//value4 = value2/value3;
            	//map.put(k, value4);
            */
            //}

            
            System.out.println("Number of components:  1");
            System.out.println("Number of edges: " + edges);
            System.out.println("Number of nodes: "+ nodes);
            System.out.println("Random jump factor: " + rjf);
            
       }
}  
