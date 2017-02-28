import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
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
        IndexWriterConfig conf = new IndexWriterConfig();

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

                FieldType token = new FieldType(StringField.TYPE_STORED);
                token.setStoreTermVectors(true);

                FieldType tokenize = new FieldType(TextField.TYPE_STORED);
                tokenize.setStoreTermVectors(true);

                Field url = new Field("url", kv.getValue(), token);
                Field content = new Field("body", Parser.parseHTML(kv.getKey()), tokenize);

                Document doc = new Document();
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