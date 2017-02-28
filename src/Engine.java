import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.util.HashMap;

public class Engine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");

			// mapping from file directory -> url
			HashMap<String, String> pages = FileOpener.getDirectories();
			indexer.buildIndex(pages.keySet());

			System.out.println(indexer.getIndexer().numDocs());

			indexer.getIndexer().close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
