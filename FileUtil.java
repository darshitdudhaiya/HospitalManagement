import java.io.*;
import java.util.*;

public class FileUtil {

    
    public static void appendLine(String filePath, String data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(data);
            bw.newLine();
        }
    }

    
    public static List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }


    public static boolean deleteLine(String filePath, String keyword) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + "_temp");

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(keyword + ",")) {
                    found = true;
                    continue;
                }
                writer.write(currentLine);
                writer.newLine();
            }
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
        }

        return found;
    }
}
