import java.util.Scanner;
import java.io.*;
public class Receiver {
    public static void main(String[] args)throws FileNotFoundException
    {
        File fin=new File("Intermediary.txt");
        Scanner scanner = new Scanner(fin);
        String[] hexValues = scanner.nextLine().split("");
        scanner.close();
        String checksum = calculateChecksum(hexValues);
        if(checksum.charAt(checksum.length()-1)=='0')
            System.out.println("No Error");
        else
            System.out.println("Error");
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
        checksum=~checksum;
        return Integer.toHexString(checksum).toUpperCase();
    }
}
