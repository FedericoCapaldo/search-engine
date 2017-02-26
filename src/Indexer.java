import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Indexer
{
    public static void main(String[] args)
    {
        JSONParser jsonParser = new JSONParser();

        try
        {
            Object object = jsonParser.parse(new FileReader("WEBPAGES_RAW/bookkeeping.json"));
            JSONObject jsonObject = (JSONObject) object;

            int counter = 0;
            for (Object key : jsonObject.keySet())
            {
                System.out.print(++counter + ": path: ");
                System.out.println(key);
            }
        }
        catch (Exception e)
        {
            System.out.println("Indexer.main: " + e);
        }
    }
}