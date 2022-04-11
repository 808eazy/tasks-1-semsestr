import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArrayWriter {
    public static void writeArray(String pathString, List<Integer> array) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathString));
        writer.write(arrayToString(array));
        writer.close();
    }

    private static String arrayToString(List<Integer> array){
        StringBuilder builtString = new StringBuilder();
        for (Integer objNow : array) {
            builtString.append(objNow);
            builtString.append(" ");
        }
        builtString.append("\n");
        return builtString.toString();
    }
}
