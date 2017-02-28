import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.file.Files;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileOpener
{
    private static String BOOK_KEEPING = "WEBPAGES_RAW/bookkeeping.json";
    private static ArrayList<String> ALL_FILES = new ArrayList<>();

    public static void main(String[] args) {
        HashMap<String, String> filesToBeIndexed = getDirectories();



        try {
            for (HashMap.Entry<String, String> kv : filesToBeIndexed.entrySet())
            {
                BufferedReader buffer = new BufferedReader(new FileReader(kv.getKey()));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = buffer.readLine()) != null)
                {
                    content.append(line + "\n");
                }

                Parser.parseHTML(kv.getKey());

                System.out.println(kv.getKey());
                buffer.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    // find all files in a directory, included its subdirectories
    public static void listOfFilesInFolder(File folder) {
        for (File singleFile : folder.listFiles()) {
            if (singleFile.isDirectory()) {
                listOfFilesInFolder(singleFile);
            }
            else if (singleFile.isFile()) {
                ALL_FILES.add(singleFile.getPath());
            }
        }
    }

    public static HashMap<String, String> getDirectories()
    {
        HashMap<String, String> directories = new HashMap<>();
        JSONParser jsonParser = new JSONParser();

        try
        {
            Object object = jsonParser.parse(new FileReader(BOOK_KEEPING));
            JSONObject jsonObject = (JSONObject) object;

            for (Object key : jsonObject.keySet())
            {
                directories.put("WEBPAGES_RAW/" + key, (String) jsonObject.get(key));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return directories;
    }
}
