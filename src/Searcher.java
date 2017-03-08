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
	private Map<String, Float> field_weights;
	private IndexSearcher indexSearcher;
	private MultiFieldQueryParser queryParser;


	public Searcher(IndexWriter indexWriter) throws IOException
	{
		field_weights = new HashMap<>();
		field_weights.put("url", 2f);
		field_weights.put("title", 10f);
		field_weights.put("h1", 8f);
		field_weights.put("h2", 7f);
		field_weights.put("h3", 6f);
		field_weights.put("strong", 5.5f);
		field_weights.put("em", 5.5f);
		field_weights.put("b", 5f);
		field_weights.put("body", 4f);
		field_weights.put("p", 4f);

//		FIELD_WEIGHTS.put("h4", 1.25f);
//		FIELD_WEIGHTS.put("h5", 1.25f);
//		FIELD_WEIGHTS.put("h6", 1.25f);
//		FIELD_WEIGHTS.put("u", 1.75f);
//		FIELD_WEIGHTS.put("i", 1.75f);

		indexSearcher = new IndexSearcher(DirectoryReader.open(indexWriter));
		queryParser = new MultiFieldQueryParser(Parser.REQUIRED_TAGS, new StandardAnalyzer(), field_weights);
	}

	public DirectoryReader getIndexReader()
	{
		return (DirectoryReader) indexSearcher.getIndexReader();
	}

	public ScoreDoc[] search(String q) throws ParseException, IOException
	{
		return indexSearcher.search(queryParser.parse(q), 5).scoreDocs;
	}
}
