import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;


public class SearchEngine
{
	public static void main(String[] args)
	{
		try
		{
			Indexer indexer = new Indexer("index");

			// comment this line out once you build index successfully to avoid rebuilding.
			//	if any changes are made to indexing options, you must reindex.
			indexer.buildIndex();




			System.out.println("Documents indexed: " + indexer.getIndexer().numDocs());

			DirectoryReader reader = DirectoryReader.open(indexer.getIndexer());

			Terms tv = reader.getTermVector(1, "content");
			TermsEnum iter = tv.iterator();
			while (iter.next() != null)
			{
				System.out.println(iter.term().utf8ToString());
			}

			IndexSearcher indexSearcher = new IndexSearcher(reader);

			indexer.getIndexer().close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
