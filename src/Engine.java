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

			for (HashMap.Entry<String, String> kv : pages.entrySet())
			{
				System.out.println(kv.getKey());

				Parser.parseHTML(kv.getKey());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
