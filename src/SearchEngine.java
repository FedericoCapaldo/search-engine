
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
//			indexer.buildIndex();
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
					System.out.printf("%6d: %6.2f: \"%s\" - %s %n", hit + 1, results[hit].score, dr.document(results[hit].doc).get("title"), dr.document(results[hit].doc).get("url") );
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
