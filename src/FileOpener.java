import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class FileOpener {

    public static void main(String[] args) {
        String filename = "WEBPAGES_RAW/0/1";
        String fileContent = "";

        try {

            ArrayList<String> filesToBeIndexed = listOfFilesInFolder(new File("WEBPAGES_RAW"));

            // the following code needs to be extented to the whole  list of line 17
            BufferedReader buffer = new BufferedReader(new FileReader(filename));

            String line ;
            fileContent = "";
            while((line = buffer.readLine()) != null) {
                fileContent += line + "\n";
            }

            System.out.println(fileContent);

            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    // find all files in a directory, included its subdirectories
    public static ArrayList<String> listOfFilesInFolder(File folder) {
        ArrayList<String> allFiles = new ArrayList<String>();
        for (File singleFile : folder.listFiles()) {
            if (singleFile.isDirectory()) {
                listOfFilesInFolder(singleFile);
            } else {
               allFiles.add(singleFile.getPath());
            }
        }

        return allFiles;
    }

}
