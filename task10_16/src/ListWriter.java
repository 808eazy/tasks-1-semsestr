import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class ListWriter {
    public static void writeToFile(String path, ArrayList<Triangle> input) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(arrayToString(input));
        writer.close();
    }

    private static String arrayToString(ArrayList<Triangle> array){
        StringBuilder builtString = new StringBuilder();
        for (Triangle triangle : array) {
            builtString.append(triangle.toString());
            builtString.append("\n");
        }
        return builtString.toString();
    }
}