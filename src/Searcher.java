import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.QueryBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Searcher
{
	private Map<String, Float> fieldWeights;
	private HashSet<String> strictFields;
	private IndexSearcher indexSearcher;
	private MultiFieldQueryParser queryParser;


	public Searcher(IndexWriter indexWriter) throws IOException
	{
		fieldWeights = new HashMap<>();
//		fieldWeights.put("url", 2f);
		fieldWeights.put("title", 3f);
		fieldWeights.put("h1", 2.5f);
		fieldWeights.put("h2", 2.25f);
		fieldWeights.put("h3", 2f);
		fieldWeights.put("strong", 2f);
		fieldWeights.put("em", 1.25f);
		fieldWeights.put("b", 1.5f);
		fieldWeights.put("a", 1.5f);
//		fieldWeights.put("body", 1.75f);
//		fieldWeights.put("p", 1.75f);
//		fieldWeights.put("table", 5f);
		fieldWeights.put("th", 2f);
		fieldWeights.put("td", 2f);

		strictFields = new HashSet<>();
		strictFields.add("title");
		strictFields.add("h1");
		strictFields.add("h2");
		strictFields.add("h3");
		strictFields.add("strong");
		strictFields.add("b");
		strictFields.add("em");

		indexSearcher = new IndexSearcher(DirectoryReader.open(indexWriter));
		queryParser = new MultiFieldQueryParser(Parser.REQUIRED_TAGS, new StandardAnalyzer(), fieldWeights);
		queryParser.setDefaultOperator(QueryParser.Operator.OR);
	}

	public DirectoryReader getIndexReader()
	{
		return (DirectoryReader) indexSearcher.getIndexReader();
	}

	public ScoreDoc[] search(String q) throws ParseException, IOException
	{
		BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
		QueryBuilder queryBuilder = new QueryBuilder(new StandardAnalyzer());

		for (int i = 0; i < Parser.REQUIRED_TAGS.length; ++i)
		{
			float weight = fieldWeights.getOrDefault(Parser.REQUIRED_TAGS[i], 1f);
			BooleanClause.Occur clause = strictFields.contains(Parser.REQUIRED_TAGS[i]) ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD;

			Query booleanQuery = queryBuilder.createBooleanQuery(Parser.REQUIRED_TAGS[i], q, clause);
			Query boostedBooleanQuery = new BoostQuery(booleanQuery, weight);

			booleanQueryBuilder.add(boostedBooleanQuery, BooleanClause.Occur.SHOULD);
		}

		System.out.println("QUERY: " + booleanQueryBuilder.build());

		booleanQueryBuilder.setMinimumNumberShouldMatch(1);
		return indexSearcher.search(booleanQueryBuilder.build(), 20).scoreDocs;
	}
}
