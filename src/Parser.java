import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser
{
    public static String[] REQUIRED_TAGS = {"h1", "h2", "h3", "h4", "h5", "h6", "strong", "b", "u", "i", "body", "em", "title", "p"};

    public static String parseHTML(String file_path) throws IOException
    {
        File html = new File(file_path);
        Document docSoup = Jsoup.parse(html, "UTF-8");

        Whitelist whitelist = new Whitelist();
        whitelist.addTags(REQUIRED_TAGS);

        return Jsoup.clean(docSoup.html(), whitelist);
    }

    public static HashMap<String, ArrayList<String>> categorizeContent(String html)
    {
        HashMap<String, ArrayList<String>> content = new HashMap<>();
        Document doc = Jsoup.parse(html).normalise();

        for (String tag : REQUIRED_TAGS)
        {
            Elements contents = doc.select(tag);

            content.put(tag, new ArrayList<>());

            for (Element e : contents)
            {
                if (e.ownText().length() > 0)
                {
                    content.get(tag).add(e.ownText());
                }
            }
        }

        return content;
    }
}
