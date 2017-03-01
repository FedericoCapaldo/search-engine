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

public class Searcher
{
	private IndexSearcher indexSearcher;
	private MultiFieldQueryParser queryParser;

	public Searcher(IndexWriter indexWriter) throws IOException
	{
		indexSearcher = new IndexSearcher(DirectoryReader.open(indexWriter));
		queryParser = new MultiFieldQueryParser(Parser.REQUIRED_TAGS, new StandardAnalyzer());
	}

	public DirectoryReader getIndexReader()
	{
		return (DirectoryReader) indexSearcher.getIndexReader();
	}

	public ScoreDoc[] search(String q) throws ParseException, IOException
	{
		Query query = queryParser.parse(q);

		TopDocs results = indexSearcher.search(query, 20);
		ScoreDoc[] docs = results.scoreDocs;

		return docs;
	}
}
