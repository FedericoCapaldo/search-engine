import org.apache.lucene.document.Document;

import java.util.HashMap;

public class Engine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");

			System.out.println(indexer.getIndexer().numDocs());

			// mapping from file directory -> url
			HashMap<String, String> pages = FileOpener.getDirectories();

			for (HashMap.Entry<String, String> kv : pages.entrySet())
			{
				//System.out.println(kv.getKey());
				//System.out.println(Parser.parseHTML(kv.getKey()));

				Document doc = new Document();

				indexer.getIndexer().addDocument(doc);
			}

			System.out.println(indexer.getIndexer().numDocs());


			indexer.getIndexer().close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
