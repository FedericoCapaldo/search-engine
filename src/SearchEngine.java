
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
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
				try
				{
					System.out.print("\n\nquery: ");
					String query = scanner.nextLine();

					if (query.equals("quit"))
					{
						break;
					}

					ScoreDoc[] results = searcher.search(query);

					for (int hit = 0; hit < results.length; ++hit)
					{
						System.out.printf("%6d: (%6.2f) %s%n                 %s%n%n",
								hit + 1,
								results[hit].score,
								dr.document(results[hit].doc).get("title"),
								dr.document(results[hit].doc).get("url"));
					}
				}
				catch (ParseException e)
				{
<<<<<<< HEAD
					System.out.println("INVALID QUERY");
=======
					System.out.printf("%6d: %6.2f: \"%s\" - %s %n", hit + 1, results[hit].score, dr.document(results[hit].doc).get("title"), dr.document(results[hit].doc).get("url") );
>>>>>>> f1b9edc9f22148b52bbea4026ec0892615418f18
				}
			}

			indexer.getIndexer().close();
			dr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
