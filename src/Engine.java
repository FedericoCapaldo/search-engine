import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.util.HashMap;

public class Engine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");
			indexer.buildIndex(FileOpener.getDirectories());
			System.out.println("NUMBER OF DOCS INDEXED: " + indexer.getIndexer().numDocs());

			DirectoryReader reader = DirectoryReader.open(indexer.getIndexer());

			System.out.println(reader.document(5));
			System.out.println(reader.getTermVector(1, "body"));


			IndexSearcher indexSearcher = new IndexSearcher(reader);

			QueryParser queryParser = new QueryParser("body", new StandardAnalyzer());
			Query query = queryParser.parse("uci pattis");
			System.out.println("Type of query: " + query.getClass().getSimpleName());
			TopDocs docs = indexSearcher.search(query, 25);
			ScoreDoc[] hits = docs.scoreDocs;


			System.out.println("Found " + hits.length + " hits.");
			for (int i=0;i<hits.length;++i)
			{
				int docId = hits[i].doc;
				Document d = reader.document(docId);
				//System.out.println(d);
				System.out.println((i + 1) + ". " + d.get("url"));
			}

			indexer.getIndexer().close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
