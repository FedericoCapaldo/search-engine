import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Indexer
{
    private IndexWriter indexer;

    public Indexer(String indexDirectory) throws IOException
    {
        Directory dir = FSDirectory.open(Paths.get(indexDirectory));
        IndexWriterConfig conf = new IndexWriterConfig();

        indexer = new IndexWriter(dir, conf);
    }
}







//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import java.io.FileReader;

//    public static void main(String[] args)
//    {
//        JSONParser jsonParser = new JSONParser();
//
//        try
//        {
//            Object object = jsonParser.parse(new FileReader("WEBPAGES_RAW/bookkeeping.json"));
//            JSONObject jsonObject = (JSONObject) object;
//
//            int counter = 0;
//            for (Object key : jsonObject.keySet())
//            {
//                System.out.print(++counter + ": path: ");
//                System.out.println(key);
//            }
//        }
//        catch (Exception e)
//        {
//            System.out.println("Indexer.main: " + e);
//        }
//    }