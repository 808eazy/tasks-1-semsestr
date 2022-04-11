import java.util.ArrayList;
import java.util.List;

public class MatrixCalculator {
    public static List<Integer> createNewList(List<Integer> array, int n){
        n = n % (array.size() + 1);
        if(n < 0) n = array.size() + n;

        System.out.println(n);

        List<Integer> result = new ArrayList<>();
        for(int newX = 0; newX < array.size() - n; newX++){
            result.add(array.get(newX + n));
        }
        for(int newX = array.size() - n; newX < array.size(); newX++){
                result.add(array.get(newX - array.size() + n));
        }
        return result;

    }
}

