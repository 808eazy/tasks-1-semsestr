public class MatrixCalculator {

    public static Integer[][] moveRows(Integer[][] array, int n) {
        n = n % (array.length + 1);
        if(n < 0) n = array.length + n;

        Integer[][] newArray = new Integer[array.length][array[0].length];
        for(int newY = 0; newY < array.length - n; newY++){
            for(int x=0; x < array[0].length; x++){
                newArray[newY][x] = array[newY+n][x];
            }
        }
        for(int newY = array.length - n; newY < array.length; newY++){
            for(int x=0; x < array[0].length; x++){
                newArray[newY][x] = array[newY- array[0].length+n][x];
            }
        }
        return newArray;
    }

    public static Integer[][] moveColumns(Integer[][] array, int n) {
        n = n % (array[0].length + 1);
        if(n < 0) n = array[0].length + n;

        Integer[][] newArray = new Integer[array.length][array[0].length];
        for(int newX = 0; newX < array[0].length - n; newX++){
            for(int y=0; y < array.length; y++){
                newArray[y][newX] = array[y][newX+n];
            }
        }
        for(int newX = array[0].length - n; newX < array.length; newX++){
            for(int y=0; y < array.length; y++){
                newArray[y][newX] = array[y][newX- array.length+n];
            }
        }
        return newArray;
    }
}
