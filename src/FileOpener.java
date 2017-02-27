import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class FileOpener {

    private static ArrayList<String> allFiles = new ArrayList<String>();

    public static void main(String[] args){
        String fileContent = "";
        ArrayList<String> filesToBeIndexed = listOfFilesInFolder(new File("WEBPAGES_RAW"));
        try {
            
            // not printing result, probably needs some check for file type.
            for(String file : filesToBeIndexed) {
                BufferedReader buffer = new BufferedReader(new FileReader(file));

                String line ;
                fileContent = "";
                while((line = buffer.readLine()) != null) {
                    fileContent += line + "\n";
                }

                // do whatever you want here (probably use parser and then feed content to Luecene)
                // System.out.println(fileContent + "\n\n\n");

                buffer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // find all files in a directory, included its subdirectories
    public static ArrayList<String> listOfFilesInFolder(File folder) {
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
