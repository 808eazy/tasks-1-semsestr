import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayReader {

    public static List<Integer> getArray(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        Scanner scanner = new Scanner(path);

        String firstRow = scanner.nextLine();
        // create arrayList, which has variable length
        List<Integer> array = new ArrayList<>();

        String[] numbersStrings = firstRow.split(" ");
        if (numbersStrings.length == 0) return null;

        for (String numberStr:numbersStrings) {
            try {
                array.add(Integer.parseInt(numberStr));
            } catch (final NumberFormatException e){
                return null;
            }
        }
        // convert ArrayList to Array
        return array;
    }
}
