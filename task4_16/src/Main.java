public class Main {
    public static void main(String[] args) {
        System.out.println(countSum(10));
    }

    public static int countSum(int n){
        int num1 = 1; // prev num
        int num2 = 1; // prev prev num
        int num = 0; // now num
        int sum = num1 + num2; // sum(first two elements set to 1)
        while(num <= n){
            // add to sum now num that always num <= n
            sum += num;
            // calculate next num
            num = num1 + num2;
            num2 = num1;
            num1 = num;

        }
        return sum;
    }
}
