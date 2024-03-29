import java.io.*;

public class Receiver {
    public static void main(String[] args) {

        try {
            // Read the contents of the file with parity
            FileInputStream fis = new FileInputStream("Intermediary.txt");
            byte[] inputBytes = fis.readAllBytes();
            fis.close();
            // Check the parity
            boolean isValid = check2DParity(inputBytes);

            if (isValid) {
                System.out.println("Parity is valid. Data is error-free.");
            } else {
                System.out.println("Parity is invalid. Data contains errors.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean check2DParity(byte[] dataWithParity) {
        int rows = (int) Math.ceil(Math.sqrt(dataWithParity.length));
        byte[][] parityMatrix = new byte[rows][rows];
        int dataIndex = 0;
        
        // Fill the parity matrix with data (including parity bits)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                //if (dataIndex < dataWithParity.length) {
                    parityMatrix[i][j] = dataWithParity[dataIndex];
                //}
                dataIndex++;
            }
        }
        // Check row and column parity
        for (int i = 0; i < rows-1; i++) {
            byte rowParity = calculateParity(parityMatrix[i]);
            if (rowParity != 0) {
                return false;
            }
            byte[] columnData = new byte[rows];
            for (int j = 0; j < rows; j++) {
                columnData[j] = parityMatrix[j][i];
            }
            byte columnParity = calculateParity(columnData);
            if (columnParity != 0) {
                return false;
            }
        }

        return true;
    }

    private static byte calculateParity(byte[] data) {
        int r,bin,f;
        byte parity=0;
        for(int a:data)
        {
            f=1;
            bin=0;
            while(a!=0)
            {
                r=a%2;
                bin=bin+r*f;
                f=f*10;
                a=a/2;
            }
            while(bin!=0)
            {
                r=bin%10;
                parity^=r;
                bin=bin/10;
            }
        }
        return parity;
    }
}
