import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ConsoleApp {
    public static void main(String[] args) {
        String inputFilePath = args[0];
        String outputFilePath = args[1];


        System.out.print("путь к входному файлу: ");
        System.out.println(inputFilePath);


        ArrayList<Triangle> input = null;

        try {
            input = ListReader.readFromFile(Paths.get(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        ArrayList<Triangle> result = new ArrayList<>();
        for (Triangle triangle : input) {
            System.out.println(triangle);
            System.out.println("лежит во всех: " + triangle.checkIfInAllCoordQuarter());
            if (triangle.checkIfInAllCoordQuarter()) result.add(triangle);
        }

        System.out.print("путь к выходному файлу: ");
        System.out.println(outputFilePath);


        try {
            ListWriter.writeToFile(outputFilePath, result);
        } catch (IOException e){
            System.err.println("невозможно записать массив в выходном файле");
            System.exit(-1);
        }

    }
}
