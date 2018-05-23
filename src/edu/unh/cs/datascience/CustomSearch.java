package edu.unh.cs.datascience;

import edu.unh.cs.treccar_v2.Data;
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

/*
 * @author Ajesh Vijayaragavan
 * based on example from TREMA-UNH and lucene 7.2.1 demo API
 */

public class customSearch {

    static class MyQueryBuilder {

        private final StandardAnalyzer analyzer;
        private List<String> tokens;

        public MyQueryBuilder(StandardAnalyzer standardAnalyzer){
            analyzer = standardAnalyzer;
            tokens = new ArrayList<String>(128);
        }

        public BooleanQuery toQuery(String queryStr) throws IOException {

            TokenStream tokenStream = analyzer.tokenStream("text", new StringReader(queryStr));
            tokenStream.reset();
            tokens.clear();
            while (tokenStream.incrementToken()) {
                final String token = tokenStream.getAttribute(CharTermAttribute.class).toString();
                tokens.add(token);
            }
            tokenStream.end();
            tokenStream.close();
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            for (String token : tokens) {
                booleanQuery.add(new TermQuery(new Term("text", token)), BooleanClause.Occur.SHOULD);
            }
            return booleanQuery.build();
        }
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("file.encoding", "UTF-8");

        if (args.length != 4) {
        	System.out.println("Format: Outlinescborfile IndexPath Pagesoutput Sectionsoutput");
        	System.exit(-1);
        } 	
        	String indexPath = args[1];
            IndexSearcher searcher = setupIndexSearcher(indexPath, "paragraph.lucene");

            searcher.setSimilarity(new BM25Similarity());
            final MyQueryBuilder queryBuilder = new MyQueryBuilder(new StandardAnalyzer());

            final String pagesFile = args[0];
            PrintWriter out = new PrintWriter(new FileWriter(args[2]));
            final FileInputStream fileInputStream3 = new FileInputStream(new File(pagesFile));
            for (Data.Page page : DeserializeData.iterableAnnotations(fileInputStream3)) {
                for (List<Data.Section> sectionPath : page.flatSectionPaths()) {
                    final String queryId = Data.sectionPathId(page.getPageId(), sectionPath);
                    String queryStr = buildSectionQueryStr(page, sectionPath);
                    TopDocs tops = searcher.search(queryBuilder.toQuery(queryStr), 100);
                    ScoreDoc[] scoreDoc = tops.scoreDocs;
                    for (int i = 0; i < scoreDoc.length; i++) {
                        ScoreDoc score = scoreDoc[i];
                        final Document doc = searcher.doc(score.doc); // to access stored content
                        final String paragraphid = doc.getField("paragraphid").stringValue();
                        final float searchScore = score.score;
                        final int searchRank = i+1;
                        out.println(queryId+" Q0 "+paragraphid+" "+searchRank + " "+searchScore+" Lucene-BM25");
                    }
                }               
            }
            out.close();

            IndexSearcher searcher2 = setupIndexSearcher(indexPath, "paragraph.lucene");

            searcher2.setSimilarity(new BM25Similarity());
            final MyQueryBuilder queryBuilder2 = new MyQueryBuilder(new StandardAnalyzer());

            final String pagesFile2 = args[0];
            PrintWriter out2 = new PrintWriter(new FileWriter(args[2]));
            final FileInputStream fileInputStream4 = new FileInputStream(new File(pagesFile2));
            for (Data.Page page : DeserializeData.iterableAnnotations(fileInputStream4)) {
                final String queryId = page.getPageId();

                String queryStr = buildSectionQueryStr(page, Collections.<Data.Section>emptyList());

                TopDocs tops = searcher2.search(queryBuilder2.toQuery(queryStr), 100);
                ScoreDoc[] scoreDoc = tops.scoreDocs;
                String paragraphid2 = null;
                for (int i = 0; i < scoreDoc.length; i++) {
                    ScoreDoc score = scoreDoc[i];
                    final Document doc = searcher2.doc(score.doc); // to access stored content
                    final String paragraphid = doc.getField("paragraphid").stringValue();
                    final float searchScore = score.score;
                    final int searchRank = i+1;
					if (paragraphid != paragraphid2)
                    	out2.println(queryId+" Q0 "+paragraphid+" "+searchRank + " "+searchScore+" Lucene-BM25");
					paragraphid2 = paragraphid;
                }
            }
            out2.close();
    }

    @NotNull
    private static IndexSearcher setupIndexSearcher(String indexPath, String typeIndex) throws IOException {
        Path path = FileSystems.getDefault().getPath(indexPath, typeIndex);
        Directory indexDir = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(indexDir);
        return new IndexSearcher(reader);
    }

    @NotNull
    private static String buildSectionQueryStr(Data.Page page, List<Data.Section> sectionPath) {
        StringBuilder queryStr = new StringBuilder();
        queryStr.append(page.getPageName());
        for (Data.Section section: sectionPath) {
            queryStr.append(" ").append(section.getHeading());
        }
        return queryStr.toString();
    }
}
