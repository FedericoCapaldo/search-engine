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

public class FileOpener
{
    private static String BOOK_KEEPING = "WEBPAGES_RAW/bookkeeping.json";
    private static ArrayList<String> ALL_FILES = new ArrayList<String>();

    public static void main(String[] args) {
        ArrayList<String> filesToBeIndexed = getDirectories();

        BufferedReader buffer;
        String fileContent;

        try {

            for (String file : filesToBeIndexed) {
                buffer = new BufferedReader(new FileReader(file));
                String line;
                fileContent = "";

                while ((line = buffer.readLine()) != null)
                {
                    fileContent += line + "\n";
                }

                // do whatever you want here (probably use parser and then feed content to Luecene)

                System.out.println(Parser.parseHTML(file));

                // should buffer.close() ? at each iteration
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

    public static ArrayList<String> getDirectories()
    {
        ArrayList<String> directories = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try
        {
            Object object = jsonParser.parse(new FileReader(BOOK_KEEPING));
            JSONObject jsonObject = (JSONObject) object;

            for (Object key : jsonObject.keySet())
            {
                directories.add("WEBPAGES_RAW/" + key);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return directories;
    }
}
