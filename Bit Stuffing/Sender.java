import java.util.Scanner;
import java.io.*;
import java.nio.file.*;
public class Sender{
        public static void main(String[] args) throws FileNotFoundException
        {
           StringBuilder input=new StringBuilder();
           FileReader fin=new FileReader("Input.txt");
           BufferedReader reader=new BufferedReader(fin);
           String line;
           try
           {
              while((line=reader.readLine())!=null)
              {
                 line = line.replaceAll("11111","111110");
                 line = "01111110" + line + "01111110";
                 input.append(line);
                 input.append("\n");
              }
              reader.close();
              Path file=Path.of("Intermediary.txt");
              Files.writeString(file,input);
           }
           catch(IOException e)
           {
              System.out.println(e);
           }
        }

}