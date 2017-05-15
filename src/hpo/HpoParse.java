package hpo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
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

public class HpoParse {
//	public static ArrayList<String> getId(String query) throws IOException{
//		FileReader file = new FileReader("hp.obo");			
//		BufferedReader reader = new BufferedReader(file);
//		String symptom = query; 
//		String line;
//		String delims = "[ ]+";
//
//
//		StandardAnalyzer analyzer = new StandardAnalyzer();
//		Directory index =  FSDirectory.open(Paths.get("IndexFiles/HPOIndex"));
//		IndexWriterConfig config = new IndexWriterConfig(analyzer);
//		config.setOpenMode(OpenMode.CREATE);
//		IndexWriter w = new IndexWriter(index, config);
//
//
//		ArrayList<String> Name = new ArrayList<String>();
//		ArrayList<String> Id = new ArrayList<String>();
//		ArrayList<String> Ref = new ArrayList<String>();
//		ArrayList<String> Synonyms = new ArrayList<String>();
//		ArrayList<String> res = new ArrayList<String>();
//		ArrayList<String> res2 = new ArrayList<String>();
//
//		while ((line = reader.readLine()) != null){
//			//System.out.println(line);
//			if (line.contains("[Term]")){
//				Document doc = new Document();
//				line = reader.readLine();
//
//				String id = "";
//				String name = "";
//				String synonyms = "";
//				String ref = "";
//
//				boolean synFound = false;
//				boolean refFound = false;
//
//				while (!line.trim().isEmpty()){
//					if (line.contains("id:") && !line.contains("alt_id:") && !line.contains("pmid:")){
//						line = line.trim();
//						String[] tokens = line.split(delims);
//						id = tokens[1];
//						Id.add(id);
//						doc.add(new TextField("ID",id,Store.YES));
//					}
//					if (line.contains("xref: MEDDRA:")){
//						refFound = true;
//						line = line.trim();
//						String[] tokens = line.split(delims);
//						ref = tokens[1];
//						String[] tokens2 = ref.split(":");
//						ref = tokens2[1];
//						Ref.add(ref);
//						doc.add(new TextField("ref",ref,Store.YES));
//
//					}
//
//					if (line.contains("name:")){
//						line = line.trim();				
//						String[] tokens = line.split(delims);
//						for (int i = 1; i < tokens.length; i++){
//							name = name + tokens[i] + " ";
//						}
//						Name.add(name);
//						doc.add(new TextField("Name",name,Store.YES));
//
//					}
//
//					if (line.contains("synonym:") && (line.contains("\""))){
//						String SynList = "";
//						synFound = true;
//						while (line.contains("synonym:")) {
//							String[] tokens = line.split("\"");
//							synonyms = tokens[1];
//							SynList = SynList + " " + synonyms;
//							line = reader.readLine();
//						}
//						Synonyms.add(SynList.trim());
//						doc.add(new TextField("Name",SynList,Store.YES));
//
//					}
//					line=reader.readLine();
//				}
//				if (!synFound){
//					Synonyms.add(synonyms);
//					doc.add(new TextField("Name",synonyms,Store.YES));
//
//				}
//				if (!refFound){
//					Ref.add(ref);
//					doc.add(new TextField("ref",ref,Store.YES));
//
//				}w.addDocument(doc);
//			}
//		}w.close();
//		//			for (int i = 0; i < Name.size(); i++){
//		//				if ((Name.get(i).toLowerCase().contains(symptom.toLowerCase())) || (Synonyms.get(i).toLowerCase().contains(symptom.toLowerCase()))){
//		//					res.add(Id.get(i));
//		//				}
//		//			}
//		//			for (int i = 0; i < Name.size(); i++){
//		//				if ((Name.get(i).toLowerCase().contains(symptom.toLowerCase())) || (Synonyms.get(i).toLowerCase().contains(symptom.toLowerCase()))){
//		//					if(!Ref.get(i).isEmpty()){
//		//						res2.add(Ref.get(i));
//		//					}
//		//				}
//		//			}
//
//
//
//
//
//		file.close();
//		ArrayList<String> s= new ArrayList<ArrayList<String>>();
//		s.add(res);
//		s.add(res2);
//		return s;
//	}
	public static ArrayList<String> Request(String requete) throws IOException, ParseException
	{		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index =  FSDirectory.open(Paths.get("IndexFiles/HpoIndex"));
		String querystr = requete;
		if(querystr.contains("*"))
		{
			Term term = new Term("Name",querystr);	
			Query q = new WildcardQuery(term);
			int hitsPerPage = 1000;
			IndexReader reader1 = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader1);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			ArrayList<String> list = new ArrayList<String>();
			if(hits.length!=0){
				for(int i=0;i<hits.length;i++){
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				list.add(d.get("ID")+"//"+d.get("ref"));
			}
				return list;
			}else{return new ArrayList<String>();}
		}
		else
	{
			Query q = new QueryParser("Name", analyzer).parse(querystr);
			int hitsPerPage = 1000;
			IndexReader reader1 = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader1);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			ArrayList<String> list = new ArrayList<String>();
			if(hits.length!=0){
				for(int i=0;i<hits.length;i++){
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				list.add(d.get("ID")+"//"+d.get("ref"));
			}
				return list;
			}else{return new ArrayList<String>();}
		}
	}
	public static void main(String[] args) throws IOException, ParseException{
		ArrayList<String> j =Request("fistula AND Rectovaginal");
		for (String h:j){
		System.out.println(h);
		}
	}
}