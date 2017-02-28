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

        // reuse existing index or create one if it does not exist
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        indexer = new IndexWriter(dir, conf);
    }

    public IndexWriter getIndexer()
    {
        return indexer;
    }

    public void addHTML()
    {

    }
}