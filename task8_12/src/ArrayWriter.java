import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArrayWriter {
    final BufferedWriter writer;

    public ArrayWriter(String pathString) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(pathString));
    }

    public void writeMatrix(Object[][] array) throws IOException {
        writer.write(MatrixToString(array));
        writer.close();
    }

    private static String MatrixToString(Object[][] array){
        StringBuilder builtString = new StringBuilder();
        for (Object[] row : array) {
            for (Object objNow : row) {
                builtString.append(objNow);
                builtString.append(" ");
            }
            builtString.append("\n");
        }
        return builtString.toString();
    }
}
