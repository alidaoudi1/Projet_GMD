package omim;

import java.io.IOException;
import java.nio.file.Paths;

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


public class Omimcsv {
	public static String Request(String requete) throws IOException, ParseException
	{		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index =  FSDirectory.open(Paths.get("OmimCsvIndex"));
		String querystr = requete;
		if (querystr.contains("*")){
			Term term = new Term("CID",querystr);
			Query q = new WildcardQuery(term);
			int hitsPerPage = 1000 ;
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs docs = searcher.search(q,hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			String list = "";
			if(hits.length!=0){
				int docId = hits[0].doc;
				Document d = searcher.doc(docId);
				list=d.get("Label")+"//"+d.get("CUI");
				return list;

			}
			else{return "kowabunga";}
		}
		else 
		{
			Query q = new QueryParser("CID", analyzer).parse(querystr);
			int hitsPerPage = 1000;
			IndexReader reader1 = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader1);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			String list = "";
			if(hits.length!=0)
			{
				int docId = hits[0].doc;
				Document d = searcher.doc(docId);
				list=d.get("Label")+"//"+d.get("CUI");
				return list;
			}else{return "kowabunga";}
		}
	}
}
