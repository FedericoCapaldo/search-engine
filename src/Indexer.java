import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;


public class Indexer
{
    private IndexWriter indexer;

    public Indexer(String indexDirectory) throws IOException
    {
        Directory directory = FSDirectory.open(Paths.get(indexDirectory));
        IndexWriterConfig configuration = new IndexWriterConfig();
        configuration.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        configuration.setRAMBufferSizeMB(64);
        configuration.setCommitOnClose(true);

        indexer = new IndexWriter(directory, configuration);
    }

    public IndexWriter getIndexer()
    {
        return indexer;
    }

    public void buildIndex() throws IOException
    {
        for (HashMap.Entry<String, String> kv : FileOpener.getDirectories().entrySet())
        {
            try
            {
                System.out.println("indexing " + kv.getKey());

                FieldType token = new FieldType(StringField.TYPE_STORED);
                token.setStoreTermVectors(true);

                FieldType tokenize = new FieldType(TextField.TYPE_STORED);
                tokenize.setStoreTermVectors(true);

                Field url = new Field("url", kv.getValue(), token);
                Field content = new Field("content", Parser.parseHTML(kv.getKey()), tokenize);

                System.out.println(Parser.categorizeContent(Parser.parseHTML(kv.getKey())));

                Document doc = new Document();
                doc.add(url);
                doc.add(content);

                indexer.addDocument(doc);
            }
            catch (IOException | IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }

        indexer.commit();
    }
}