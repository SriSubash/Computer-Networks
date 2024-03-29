import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
public class Receiver
{
        public static void main(String[] args) throws FileNotFoundException
        {
           StringBuilder input=new StringBuilder();
           FileReader fin=new FileReader("Intermediary.txt");
           BufferedReader reader=new BufferedReader(fin);
           String line;
           try
           {
              while((line=reader.readLine())!=null)
              {
                 line = removeBoundaryDelimiter(line, "01111110");
                 line = removeZeroAfterFiveOnes(line);
                 input.append(line);
                 input.append("\n");
              }
              reader.close();
              Path file=Path.of("Output.txt");
              Files.writeString(file,input);
           }
           catch(IOException e)
           {
              System.out.println(e);
           }
        }

        private static String removeBoundaryDelimiter(String binaryString, String delimiter)
        {
                if (binaryString.startsWith(delimiter))
                {
                        binaryString = binaryString.substring(delimiter.length());
                }

                if (binaryString.endsWith(delimiter))
                {
                        binaryString = binaryString.substring(0, binaryString.length() - delimiter.length());
                }
                return binaryString;
                                                                                                                                                                                                                      }
                                                                                                                                                                                                                      private static String removeZeroAfterFiveOnes(String binaryString)
         {
                String pattern = "111110";
                String replacement = "11111";

                return binaryString.replaceAll(pattern, replacement);
         }
}