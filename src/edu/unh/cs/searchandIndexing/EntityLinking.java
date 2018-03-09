package lucene.searchandIndexing;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.Data.ParaBody;
import edu.unh.cs.treccar_v2.Data.ParaLink;
import edu.unh.cs.treccar_v2.Data.Section;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jetbrains.annotations.NotNull;
import org.omg.Messaging.SyncScopeHelper;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.BufferedReader;
import java.io.File;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


/*
 * @author Ajesh Vijayaragavan
 */

public class EntityLinking {

    public static void main(String[] args) throws IOException {
        System.setProperty("file.encoding", "UTF-8");

        if (args.length != 2) {
        	System.out.println("Format: ParagraphsFile OutFile");
        	System.exit(-1);
        }
        
        //lead paragraphs
        final String ParagraphsFile = args[0];
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));
        final FileInputStream fileInputStream = new FileInputStream(new File(ParagraphsFile));
               
        List<String> paragraphs1 = new ArrayList<String>();
        Map<String, List<String>> TrecEntitylinks = new HashMap<String, List<String>>();
        
		//Entity links from treccar
        for(Data.Paragraph paragraph: DeserializeData.iterableParagraphs(fileInputStream)) {
        	paragraphs1.add(paragraph.getTextOnly());
        	TrecEntitylinks.put(paragraph.getParaId(), paragraph.getEntitiesOnly());
        	        	
        	for (Data.ParaBody ParaBody: paragraph.getBodies()) {
        		if (ParaBody instanceof Data.ParaLink)
        		{
        			out.println(((Data.ParaLink) ParaBody).getAnchorText());
        			out.println(((Data.ParaLink) ParaBody).getPage());
        		}   	       			
        	}
    }
        out.close();
        String url = null;
        List<String> url2 = new ArrayList<String>();
        List<String> DbpediaLinks = new ArrayList<String>();
        
		//DBPedia Spotlight
        for (String item : paragraphs1)
        {
	        url = "http://model.dbpedia-spotlight.org/en/annotate?text=" + URLEncoder.encode(item, "UTF-8");
	        url2.add(url);
        }
        
        for (String item2 : url2)
        {
        	
       	 try {
                 HttpClient client = HttpClientBuilder.create().build();
                 HttpGet request = new HttpGet(item2);
                 HttpResponse response = client.execute(request);
      
                 int responseCode = response.getStatusLine().getStatusCode();
                 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                 String line = "";
                 StringBuilder sb = new StringBuilder();
                 while ((line = rd.readLine()) != null) {
                     sb.append(line.trim());
                     //DbpediaLinks.add(line.surfaceForm());
                 }
     
             } catch (ClientProtocolException e) {
                 e.printStackTrace();
             } catch (UnsupportedOperationException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }        
			 
        	 System.exit(-1); 
         }

        }
}