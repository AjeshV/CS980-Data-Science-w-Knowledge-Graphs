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
import org.jetbrains.annotations.NotNull;
import org.omg.Messaging.SyncScopeHelper;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.io.File;

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
        for(Data.Paragraph paragraph: DeserializeData.iterableParagraphs(fileInputStream)) {
        	//out.println(paragraph.getTextOnly());
        	//out.println(paragraph.getBodies());
        	for (Data.ParaBody ParaBody: paragraph.getBodies()) {
        		//out.println((Data.ParaLink)ParaBody.);
        		//out.println(((Data.ParaLink)ParaBody).getAnchorText());
        		if (ParaBody instanceof Data.ParaLink)
				//hasLinkSection() can be null or not null
        		//if (((Data.ParaLink) ParaBody).hasLinkSection())
        		{
        		//Hyper link is represented by getAnchorText() and page it refers to by getPage()
        			out.println(((Data.ParaLink) ParaBody).getAnchorText());
        			out.println(((Data.ParaLink) ParaBody).getPage());
        		}
/*        	for (Data.ParaBody ParaLink: paragraph.getBodies())
        	{
        		//	out.println(((Data.ParaLink) ParaLink).getAnchorText());
        		//}
        		out.println(((Data.ParaLink) ParaLink).getAnchorText());
        	}*/
        	
        			
        	}
         //}
    }
        out.close();

}
}