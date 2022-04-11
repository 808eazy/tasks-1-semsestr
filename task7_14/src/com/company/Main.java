package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите массив");
        Integer[] inputtedArray = Array.inputArray(scanner.nextLine());

        while (inputtedArray == null){

            System.out.println("Введены некорректные данные");
            inputtedArray = Array.inputArray(scanner.nextLine());
        }

        Array array = new Array(inputtedArray);
        array.printArray();


        int result = array.solution();

        System.out.print(" позиция последнего элемента массива, который\n" +
                "граничит с максимальным или минимальным элементом массива, однако сам не\n" +
                "является минимальным или максимальным элементом:\n");
        System.out.println(result);
    }
}
