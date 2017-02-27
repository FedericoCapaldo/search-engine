import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.file.Files;
import java.rmi.server.ExportException;
import java.util.ArrayList;

public class FileOpener {

    private static ArrayList<String> allFiles = new ArrayList<String>();

    public static void main(String[] args){
        String fileContent = "";
        ArrayList<String> filesToBeIndexed = listOfFilesInFolder(new File("WEBPAGES_RAW"));
        try {

            BufferedReader buffer ;

            for(String file : filesToBeIndexed) {
                buffer = new BufferedReader(new FileReader(file));

                String line ;
                fileContent = "";
                while((line = buffer.readLine()) != null) {
                    fileContent += line + "\n";
                }

                // do whatever you want here (probably use parser and then feed content to Luecene)

                System.out.println(fileContent + "\n\n\n");

                // should buffer.close() ? at each iteration
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
                try {
                    // checking that the filetype is/contains html to avoid indexing of json and other filetypes
                    if(Files.probeContentType(singleFile.toPath()).contains("html")) {
                        allFiles.add(singleFile.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return allFiles;
    }

}
