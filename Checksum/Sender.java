import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
public class Sender {
    public static void main(String[] args)throws FileNotFoundException
    {
        File fin=new File("Input.txt");
        Scanner scanner = new Scanner(fin);
        String[] hexValues = scanner.nextLine().split("");
        scanner.close();
        String checksum = calculateChecksum(hexValues);
        StringBuilder output=new StringBuilder();
        for(String hexvalue:hexValues)
        {
            output.append(hexvalue);
        }
        output.append(checksum.charAt(checksum.length()-1));
        try{
        Path file=Path.of("Intermediary.txt");
        Files.writeString(file,output);
        scanner.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    private static String calculateChecksum(String[] hexValues) {
        int checksum = 0;

        for (String hexValue : hexValues) {
            try {
                int intValue = Integer.parseInt(hexValue, 16);
                checksum ^= intValue;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid hexadecimal value - " + hexValue);
                return null;
            }
        }
        int carry = checksum>>5;
        System.out.println(carry);
        checksum=~checksum;
        return Integer.toHexString(checksum).toUpperCase();
    }
}
