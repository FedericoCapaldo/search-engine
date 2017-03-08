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
//		field_weights.put("url", 10f);
		field_weights.put("title", 10f);
		field_weights.put("h1", 7f);
		field_weights.put("h2", 6f);
		field_weights.put("h3", 5f);
		field_weights.put("strong", 4f);
		field_weights.put("em", 4f);
		field_weights.put("b", 2f);
		field_weights.put("body", 1f);
		field_weights.put("p", 1f);
//		field_weights.put("table", 5)
//		field_weights.put("th", 1.5f);
//		field_weights.put("td", 1.5f);

		indexSearcher = new IndexSearcher(DirectoryReader.open(indexWriter));
		queryParser = new MultiFieldQueryParser(Parser.REQUIRED_TAGS, new StandardAnalyzer(), field_weights);
	}

	public DirectoryReader getIndexReader()
	{
		return (DirectoryReader) indexSearcher.getIndexReader();
	}

	public ScoreDoc[] search(String q) throws ParseException, IOException
	{
		return indexSearcher.search(queryParser.parse(q), 30).scoreDocs;
	}
}
