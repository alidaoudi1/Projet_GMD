package omim;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Omimtxt {
	public static ArrayList<String> Request(String requete) throws IOException, ParseException
	{	
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index =  FSDirectory.open(Paths.get("OmimIndex"));
		String querystr = requete;
		if (querystr.contains("*")){
			Term term = new Term("CS",querystr);
			Query q = new WildcardQuery(term);
			int hitsPerPage = 1000 ;
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs docs = searcher.search(q,hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			ArrayList<String> list = new ArrayList<String>();
			if(hits.length!=0){
				for(int i=0;i<hits.length;i++) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					list.add("OMIM//"+d.get("ID"));
					
				}
				return list;
			}
			else{return new ArrayList<String>();}
		}
		else 
		{
			Query q = new QueryParser("CS", analyzer).parse(querystr);
			int hitsPerPage = 1000;
			IndexReader reader1 = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader1);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			ArrayList<String> list = new ArrayList<String>();
			if(hits.length!=0){
				for(int i=0;i<hits.length;i++) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					list.add("OMIM//"+d.get("ID"));
					
				}return list;
			}
			else{return new ArrayList<String>();}
		}

	}
}
