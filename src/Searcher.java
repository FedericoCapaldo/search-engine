import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Searcher
{
	private static Map<String, Float> FIELD_WEIGHTS;
	static
	{
		FIELD_WEIGHTS = new HashMap<>();
		FIELD_WEIGHTS.put("title", 125f);
		FIELD_WEIGHTS.put("h1", 75f);
		FIELD_WEIGHTS.put("h2", 60f);
		FIELD_WEIGHTS.put("h3", 45f);
		FIELD_WEIGHTS.put("h4", 100f);
		FIELD_WEIGHTS.put("h5", 100f);
		FIELD_WEIGHTS.put("h6", 100f);
		FIELD_WEIGHTS.put("strong", 30f);
		FIELD_WEIGHTS.put("em", 25f);
		FIELD_WEIGHTS.put("b", 100f);
		FIELD_WEIGHTS.put("u", 100f);
		FIELD_WEIGHTS.put("i", 100f);
		FIELD_WEIGHTS.put("p", 50f);
	}

	private IndexSearcher indexSearcher;
	private MultiFieldQueryParser queryParser;


	public Searcher(IndexWriter indexWriter) throws IOException
	{
		indexSearcher = new IndexSearcher(DirectoryReader.open(indexWriter));
		queryParser = new MultiFieldQueryParser(Parser.REQUIRED_TAGS, new StandardAnalyzer(), FIELD_WEIGHTS);
	}

	public DirectoryReader getIndexReader()
	{
		return (DirectoryReader) indexSearcher.getIndexReader();
	}

	public ScoreDoc[] search(String q) throws ParseException, IOException
	{
		Query query = queryParser.parse(q);

		TopDocs results = indexSearcher.search(query, 100);
		ScoreDoc[] docs = results.scoreDocs;

		return docs;
	}
}
