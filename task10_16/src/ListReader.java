import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ListReader {

    public static ArrayList<Triangle> readFromFile(Path path) throws IOException, IllegalArgumentException {
        Scanner scanner = new Scanner(path);
        ArrayList<Triangle> triangles = new ArrayList<>();
        while (scanner.hasNext()) {
            try {
                triangles.add(Triangle.getFromString(scanner.nextLine()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        }
        return triangles;
    }
}

