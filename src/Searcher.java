import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Searcher
{
	private static Map<String, Float> FIELD_WEIGHTS;
	static
	{
		FIELD_WEIGHTS = new HashMap<>();
		FIELD_WEIGHTS.put("title", 90000f);
		FIELD_WEIGHTS.put("h1", 4f);
		FIELD_WEIGHTS.put("h2", 3.5f);
		FIELD_WEIGHTS.put("h3", 3.25f);
		FIELD_WEIGHTS.put("h4", 3f);
		FIELD_WEIGHTS.put("h5", 2.75f);
		FIELD_WEIGHTS.put("h6", 2.5f);
		FIELD_WEIGHTS.put("strong", 2.25f);
		FIELD_WEIGHTS.put("em", 2f);
		FIELD_WEIGHTS.put("b", 1.75f);
		FIELD_WEIGHTS.put("u", 1.75f);
		FIELD_WEIGHTS.put("i", 1.75f);
		FIELD_WEIGHTS.put("p", 1.25f);
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
		// TODO: try tokenizing URL and search with URL field too with some weight
		Query query = queryParser.parse(q);
		TopDocs results = indexSearcher.search(query, 25);
		ScoreDoc[] docs = results.scoreDocs;

		return docs;
	}
}
