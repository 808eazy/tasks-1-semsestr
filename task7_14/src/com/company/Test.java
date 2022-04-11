package com.company;

public class Test {
    public static void main(String[] args) throws Exception {
        test(new Integer[]{4, 5, 6, 6, 3, 2, 2}, 4);
        test(new Integer[]{3, 6, 6, 6 , 2, 2}, 0);
        test(new Integer[]{6, 6, 6 , 2, 2, 3}, 5);
        test(new Integer[]{1, 6, 2, 6 , 1, 1}, 2);
        test(new Integer[]{1}, -1);
        test(new Integer[]{}, -1);
        test(new Integer[]{2, 5, 3}, 2);
        test(new Integer[]{2, 3, 5}, 1);
        test(new Integer[]{6, 6, 6, 4, 1, 1, 0}, 5);
        test(new Integer[]{1, 1, 1, 6, 6, 6, 1, 1, 1}, -1);
        test(new Integer[]{1, 2, 3, 4, 5}, 3);
    }

    public static void test(Integer[] inputArray, int expectedRes) throws Exception {
        Array array = new Array(inputArray);
        if(expectedRes != array.solution()) {

            array.printArray();
            System.out.println("\nВстречно: " + expectedRes + ", но верно:" + array.solution());
            throw new Exception("Неверно");
        }
        System.out.println("Верно");
    }
}