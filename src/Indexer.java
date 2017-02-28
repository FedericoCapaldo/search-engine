import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

public class Indexer
{
    private IndexWriter indexer;

    public Indexer(String indexDirectory) throws IOException
    {
        Directory dir = FSDirectory.open(Paths.get(indexDirectory));
        IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());

        // reuse existing index or create one if it does not exist
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        indexer = new IndexWriter(dir, conf);
    }

    public IndexWriter getIndexer()
    {
        return indexer;
    }

    public void buildIndex(HashMap<String, String> pages) throws IOException
    {
        for (HashMap.Entry<String, String> kv : pages.entrySet())
        {
            try
            {
                System.out.println("Indexing: " + kv.getKey());

                Document doc = new Document();

                StringField url = new StringField("url", kv.getValue(), Field.Store.YES);
                TextField content = new TextField("body", Parser.parseHTML(kv.getKey()), Field.Store.NO);

                doc.add(url);
                doc.add(content);

                indexer.addDocument(doc);
            }
            catch (IOException | IllegalArgumentException e)
            {

            }
        }
    }
}