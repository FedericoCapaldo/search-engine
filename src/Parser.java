import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.File;
import java.io.IOException;

public class Parser
{
    public static String parseHTML(String file_path) throws IOException
    {
        String ret = null;

        File html = new File(file_path);
        Document docSoup = Jsoup.parse(html, "UTF-8");
        String[] requiredTags = {"h1", "h2", "h3", "strong", "b", "body", "em"};

        Whitelist whitelist = new Whitelist();
        whitelist.addTags(requiredTags);
        ret = Jsoup.clean(docSoup.html(), whitelist);

        return ret;
    }

    public static String categorizeContent(String html)
    {
        Document d = Jsoup.parse(html);
    }
}
