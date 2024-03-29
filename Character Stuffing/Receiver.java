import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
public class Receiver1 {
    public static void main(String[] args) throws FileNotFoundException {
        File fin=new File("Intermediary.txt");
        Scanner scanner = new Scanner(fin);
        String inputString = scanner.nextLine();
        scanner.close();

        String unstuffedString = unstuffString(inputString);

        try{
        Path file=Path.of("Output.txt");
        Files.writeString(file,unstuffedString);
        scanner.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    private static String unstuffString(String inputString) {
        StringBuilder stuffedString = new StringBuilder();
        inputString=inputString.replaceAll("ESC ESC", "ESC");
        inputString=inputString.replaceAll("FLAG ESC", "FLAG");

        for (int i = 4; i < inputString.length()-4; i++) {
            char currentChar = inputString.charAt(i);
            stuffedString.append(currentChar);

            if (Character.isDigit(currentChar) && i + 3 < inputString.length() &&
                    Character.isLetter(inputString.charAt(i + 1))) {
                currentChar = inputString.charAt(i+1);
                stuffedString.append(currentChar);
                i+=4;
            }
        }
        return stuffedString.toString();
    }
    
}
