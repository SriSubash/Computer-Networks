import java.io.*;

public class Sender {
    public static void main(String[] args) {

        try {
            // Read the contents of the input file
            FileInputStream fis = new FileInputStream("Input.txt");
            byte[] inputBytes = fis.readAllBytes();
            fis.close();

            // Calculate the 2-dimensional parity
            byte[][] parityMatrix = generate2DParity(inputBytes);

            // Write the parity matrix along with the data to the output file
            FileOutputStream fos = new FileOutputStream("Intermediary.txt");
            writeParityMatrix(fos, parityMatrix);
            fos.close();

            //System.out.println("2-dimensional parity generated and written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[][] generate2DParity(byte[] data) {
        int rows = (int) Math.ceil(Math.sqrt(data.length));
        byte[][] parityMatrix = new byte[rows+1][rows+1];
        int dataIndex = 0;

        // Fill the parity matrix with data
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (dataIndex < data.length) {
                    parityMatrix[i][j] = data[dataIndex];
                } else {
                    // Padding with 0 if data is not enough to fill the matrix
                    parityMatrix[i][j] = 0;
                }
                dataIndex++;
            }
        }

        // Calculate parity for each row and column
        for (int i = 0; i < rows; i++) {
            byte rowParity = calculateParity(parityMatrix[i]);
            parityMatrix[i][rows] = rowParity;
            byte[] columnData = new byte[rows];
            for (int j = 0; j < rows; j++) {
                columnData[j] = parityMatrix[j][i];
            }
            byte columnParity = calculateParity(columnData);
            parityMatrix[rows][i] = columnParity;
        }

        for(int i=0;i<rows+1;i++)
        {
            for (int j=0;j<rows+1;j++)
            {
                System.out.print(parityMatrix[i][j]+" ");
            }
            System.out.println();
        }
        //parityMatrix[0][0]=97;
        return parityMatrix;
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

    private static void writeParityMatrix(FileOutputStream fos, byte[][] parityMatrix) throws IOException {
        for (byte[] row : parityMatrix) {
            fos.write(row);
        }
    }
}
