import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Parser
{
    public static String[] REQUIRED_TAGS = {"h1", "h2", "h3", "strong", "b", "body", "em"};

    public static String parseHTML(String file_path) throws IOException
    {
        File html = new File(file_path);
        Document docSoup = Jsoup.parse(html, "UTF-8");

        Whitelist whitelist = new Whitelist();
        whitelist.addTags(REQUIRED_TAGS);

        return Jsoup.clean(docSoup.html(), whitelist);
    }

    public static String categorizeContent(String html)
    {
        Document doc = Jsoup.parse(html).normalise();

        HashMap<String, String> content = new HashMap<>();

        // TODO: Tokenize titles into own fields, or combine all tags into one field?

        for (String tag : REQUIRED_TAGS)
        {
            System.out.println("^^^^^^vvvvvv\n" + tag + ":\n");

            Elements contents = doc.select(tag);
            System.out.println(doc.select(tag));

            System.out.println("---");

            for (Element e : contents)
            {
                System.out.println(e.ownText());
            }

            System.out.println("-----------");
        }

        return "";
    }
}
