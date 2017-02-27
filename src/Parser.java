import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.File;

public class Parser {
    public static String parseHTML(String file_path){
        String ret = null;
        try{
            File html = new File(file_path);
            Document docSoup = Jsoup.parse(html, "UTF-8");
            String[] requiredTags = {"h1", "h2", "h3", "strong", "b", "body"};

            Whitelist whitelist = new Whitelist();
            whitelist.addTags(requiredTags);
            ret = Jsoup.clean(docSoup.html(), whitelist);
        }
        catch (Exception ex){
            System.out.println("ERROR: File not in relative path");
        }
        return ret;
    }
}
