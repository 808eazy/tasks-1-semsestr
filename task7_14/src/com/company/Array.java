package com.company;

import java.util.ArrayList;
import java.util.List;

public class Array {

    private final Integer[] array;
    public Array(Integer[] array){ // if we need input from a code(e.g. for tests)
        this.array = array;
    }

    public static Integer[] inputArray(String input){
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

    public void printArray(){
        System.out.print("{");

        for (int i = 0; i < array.length; i++) {

            int num = array[i];
            System.out.print(num);
            if(i < array.length-1) System.out.print(", ");
        }
        System.out.print("} ");
    }

    public Integer[] getArray(){
        return this.array;
    }


    public int solution(){

        if(array.length == 0) return -1;
        int min = this.array[0];
        int max = this.array[0];
        for(int num: array){
            if(num > max) max = num;
            if(num < min) min = num;
        }
        int lastPos = -1;
        for (int i = 0; i < array.length; i++) {

            int num = array[i];
            if(num == min || num == max){
                if(i != 0) {
                    if(array[i-1] != min && array[i-1] != max) lastPos = i-1;
                }
                if(i != array.length-1) {
                    if(array[i+1] != min && array[i+1] != max) lastPos = i+1;
                }
            }
        }
        return lastPos;

    }


}

