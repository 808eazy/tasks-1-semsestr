import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayReader {
    final Scanner scanner;

    public ArrayReader(String pathString) throws java.io.IOException {
        Path path = Paths.get(pathString);
        this.scanner = new Scanner(path);

    }

    public Integer[][] getArray() throws FileDataException {
        // create array list of Integer arrays variable length
        ArrayList<Integer[]> resultArrayList = new ArrayList<>();
        Integer[] rowArray;

        while (this.scanner.hasNext()){
            rowArray = arrayFromString(this.scanner.nextLine());
            if(rowArray == null) throw new FileDataException("File incorrect");
            if(rowArray.length == 0) continue;
            resultArrayList.add(rowArray);

        }

        int width = resultArrayList.get(0).length;
        int height = resultArrayList.size();
        // create immutable integer array with calculated width and height
        Integer[][] result = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            if(resultArrayList.get(i).length != width) throw new FileDataException("array not rectangle");
            result[i] = resultArrayList.get(i);
        }
        return result;
    }


    public static class FileDataException extends Exception {
        public FileDataException(String errorMessage) {
            super(errorMessage);
        }
    }

    private Integer[] arrayFromString(String input){
        // create arrayList, which has variable length
        List<Integer> ArrayListTemp = new ArrayList<>();

        String[] numbersStrings = input.split(" ");
        if (numbersStrings.length == 0) return null;

        for (String numberStr:numbersStrings) {
            try {
                ArrayListTemp.add(Integer.parseInt(numberStr));
            } catch (final NumberFormatException e){
                return null;
            }
        }
        // convert ArrayList to Array
        Integer[] array = new Integer[ArrayListTemp.size()];
        array = ArrayListTemp.toArray(array);
        return array;
    }
}
