import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
        IndexWriterConfig conf = new IndexWriterConfig();

        // reuse existing index or create one if it does not exist
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        indexer = new IndexWriter(dir, conf);
    }

    public IndexWriter getIndexer()
    {
        return indexer;
    }

    public void buildIndex(Set<String> paths) throws IOException
    {
        for (String path : paths)
        {
            System.out.println(Parser.parseHTML(path));

            Document doc = new Document();

            TextField field = new TextField("a", "b", Field.Store.YES);
            doc.add(field);

            indexer.addDocument(doc);
        }
    }
}