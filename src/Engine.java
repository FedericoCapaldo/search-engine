import java.util.HashMap;

public class Engine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");
			HashMap<String, String> webpages = FileOpener.getDirectories();

			for (HashMap.Entry<String, String> kv : webpages.entrySet())
			{
				System.out.println(kv.getKey());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
