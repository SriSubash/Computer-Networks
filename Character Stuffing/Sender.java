import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Sender1 {
    public static void main(String[] args) throws FileNotFoundException {
        File fin=new File("Input.txt");
        Scanner scanner = new Scanner(fin);
        String inputString = scanner.nextLine();
        scanner.close();

        String stuffedString = stuffString(inputString);

        try{
        Path file=Path.of("Intermediary.txt");
        Files.writeString(file,stuffedString);
        scanner.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    private static String stuffString(String inputString) {
        StringBuilder stuffedString = new StringBuilder();
        inputString=inputString.replaceAll("ESC", "ESC ESC");
        inputString=inputString.replaceAll("FLAG", "FLAG ESC");
        stuffedString.append("FLAG");

        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            stuffedString.append(currentChar);

            if (Character.isDigit(currentChar) && i + 1 < inputString.length() &&
                    Character.isLetter(inputString.charAt(i + 1))) {
                currentChar = inputString.charAt(i+1);
                stuffedString.append(currentChar);
                stuffedString.append("ESC");
                i++;
            }
        }

        // Append FLAG at the end
        stuffedString.append("FLAG");

        return stuffedString.toString();
    }
}