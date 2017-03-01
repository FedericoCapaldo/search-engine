
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;

import java.io.PrintWriter;
import java.util.*;


public class SearchEngine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");

			// comment this line out once you build index successfully to avoid rebuilding.
			//	if any changes are made to indexing options, you must reindex.
//			indexer.buildIndex();
			System.out.println("Documents indexed: " + indexer.getIndexer().numDocs());

			DirectoryReader reader = DirectoryReader.open(indexer.getIndexer());

			Terms tv = reader.getTermVector(5, "body");

			IndexSearcher indexSearcher = new IndexSearcher(reader);

			indexer.getIndexer().close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

//	Map<String, Integer> frequencies = new TreeMap<>();
//
//			for (int docID = 0; docID < reader.maxDoc(); ++docID)
//		{
//		Fields fields = reader.getTermVectors(docID);
//		Iterator<String> fieldsIterator = fields.iterator();
//
//		while (fieldsIterator.hasNext())
//		{
//		String fieldName = fieldsIterator.next();
//
//		if (fieldName.equals("url") == false)
//		{
//		Terms terms = fields.terms(fieldName);
//
//		TermsEnum termsIterator = terms.iterator();
//
//		while (termsIterator.next() != null)
//		{
//		String term = termsIterator.term().utf8ToString();
//
//		if (frequencies.containsKey(term))
//		{
//		frequencies.put(term, frequencies.get(term) + 1);
//		}
//		else
//		{
//		frequencies.put(term, 1);
//		}
//		}
//		}
//		}
//		}
//
//
//		Map<Integer, List<String>> sortedFrequencies = new TreeMap<>();
//
//		for (Map.Entry<String, Integer> kv : frequencies.entrySet())
//		{
//		if (!sortedFrequencies.containsKey(kv.getValue()))
//		{
//		sortedFrequencies.put(kv.getValue(), new ArrayList<String>());
//		}
//
//		sortedFrequencies.get(kv.getValue()).add(kv.getKey());
//		}
//
//
//		for (Map.Entry<Integer, List<String>> kv : sortedFrequencies.entrySet())
//		{
//		writer.printf("%-10d %s%n", kv.getKey(), kv.getValue().toString());
//		}