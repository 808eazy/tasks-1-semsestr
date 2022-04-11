import java.io.IOException;
import java.util.List;

public class ConsoleApp {
    public static void main(String[] args) {
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        int n = Integer.parseInt(args[2]);

        System.out.print("Путь к выходному файлу: ");
        System.out.println(inputFilePath);

        List<Integer> gotArray = null;

        try {
            gotArray = ArrayReader.getArray(inputFilePath);
        } catch (IOException e) {
            System.err.println("недопустимый файл");
            System.exit(-1);
        }

        System.out.println("получен массив");
        printArray(gotArray);


        List<Integer> result = MatrixCalculator.createNewList(gotArray, n);

        System.out.println("результат расчетов");
        printArray(result);

        System.out.print("путь к выходному файлу: ");
        System.out.println(outputFilePath);


        try {
            ArrayWriter.writeArray(outputFilePath, result);
        } catch (IOException e){
            System.err.println("невозможно записать массив в выходной файл");
            System.exit(-1);
        }

    }
    public static void printArray(List<Integer> array){
        System.out.println(array.toString());
    }
}
