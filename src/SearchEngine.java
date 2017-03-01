
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.ScoreDoc;

import java.util.Scanner;


public class SearchEngine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");
			indexer.buildIndex();
			Searcher searcher = new Searcher(indexer.getIndexer());
			DirectoryReader dr = searcher.getIndexReader();
			Scanner scanner = new Scanner(System.in);

			while (true)
			{
				System.out.print("\n\nQUERY: ");
				String query = scanner.nextLine();

				if (query.equals("quit"))
				{
					break;
				}

				ScoreDoc[] results = searcher.search(query);

				for (int hit = 0; hit < results.length; ++hit)
				{
					System.out.printf("%6d: %6.2f: %s%n", hit + 1, results[hit].score, dr.document(results[hit].doc).get("url"));
				}
			}

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