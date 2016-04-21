import java.io.*;
import java.util.ArrayList;

/**
 * Created by Natnat on 01.11.2015.
 */
public class FileHandler {

    public ArrayList<String> readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = br.readLine();
            ArrayList<String> liste = new ArrayList<String>();

            while (line != null) {
                liste.add(line);
                line = br.readLine();
            }
            return liste;
        } finally {
            br.close();
        }
    }

    public void createFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println("Default Entry");
        writer.close();
    }

    public void createDirectory(String name) {
        // Creating new directory in Java, if it doesn't exists
        boolean success = false;
        String dir = name;

        File directory = new File(dir);
        if (directory.exists()) {
            System.out.println("Directory already exists ...");
        } else {
            System.out.println("Directory not exists, creating now");
            success = directory.mkdir();
            if (success) {
                System.out.printf("Successfully created new directory : %s%n", dir);
            } else {
                System.out.printf("Failed to create new directory: %s%n", dir);
            }
        }
    }


    public void updateLine(String fileName, String toUpdate, String updated) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line;
        String input = "";

        while ((line = file.readLine()) != null)
            input += line + System.lineSeparator();

        input = input.replace(toUpdate, updated);

        FileOutputStream os = new FileOutputStream(fileName);
        os.write(input.getBytes());

        file.close();
        os.close();
        System.out.println("Zeile wurde aktualisiert: " + updated);
    }


}
