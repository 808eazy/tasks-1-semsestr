package com.company;

public class Main {
    public static void main(String[] args) {
        System.out.println(countSum(10));
    }

    public static int countSum(int n){

        int num1 = 1;
        int num2 = 1;
        int num = 0;
        int sum = num1 + num2;

        while(num <= n){

            sum = sum + num;

            num = num1 + num2;
            num2 = num1;
            num1 = num;

        }
        return sum;
    }
}
