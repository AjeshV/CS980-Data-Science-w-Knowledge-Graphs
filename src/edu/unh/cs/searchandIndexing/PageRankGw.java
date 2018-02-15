package edu.unh.cs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class PageRankGw {
	
	public static final String file1 = args[0] ;
	public static final String rjf = 0.15;
	int edges = 0;
	int nodes = 0;
	float start = (1/nodes);
	float outlinks = 0;

    public static void main(String[] args) throws FileNotFoundException {
    	
        if (args.length != 1) {
        	System.out.println("Format: input Graph adjacency list");
        	System.exit(-1);
        }
    }
    	Scanner scanner = new Scanner(new FileReader(file1));
            
    		HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
           
            while (scanner.hasNextLine()) {
            	String[] columns = scanner.nextLine().split("\\s+");
            	for (int i = 1; i < columns.length; i++) {
            		ArrayList<String> list = new ArrayList<String>();
            		list.add(columns[i]);
            		edges += 1;
            	}
            	
            	for (int j = 1; j < columns.length; i++) {
            		hashMap.put("columns[0]", list);
            		nodes += 1;
            	}
				
    for(int i=1;i<=nodes;i++)
	{
		pagerank[i] = start;
	}   

	int iter = 1;
	while(iter<=2)
	{
		for(int i=1;i<=nodes;i++)
		{  
			Tmp[i]=this.pagerank[i];
		} 
			pagerank+=Tmp[i]*(1/outlinks);
	}  		
            System.out.println("Number of components: " + args.length);
            System.out.println("Number of edges: " + (nodes+edges));
            System.out.println("Number of nodes: " + nodes);
            System.out.println("Random jump factor: " + rjf);
            	
            }
        }
    }

