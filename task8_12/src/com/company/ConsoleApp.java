package com.company;

import java.io.IOException;
import java.util.Objects;

public class ConsoleApp {

    public static void main(String[] args) {
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String whatMove = args[2];
        int n = Integer.parseInt(args[3]);

        // get array from file
        ArrayReader arrayReader = null;
        Integer[][] gotArray = null;
        try {
            arrayReader = new ArrayReader(inputFilePath);
        } catch (java.io.IOException e) {
            System.err.println("file not found");
            System.exit(-1);
        }

        try {
            gotArray = arrayReader.getArray();
        } catch (ArrayReader.FileDataException e) {
            System.err.println("invalid file");
            System.exit(-1);
        }

        System.out.print("input file path: ");
        System.out.println(inputFilePath);

        System.out.println("got array");
        printMatrix(gotArray);
        Integer[][] result;
        if(Objects.equals(whatMove, "row")) {
            result = MatrixCalculator.moveRows(gotArray, n);
        }
        else if(Objects.equals(whatMove, "column")) {
            result = MatrixCalculator.moveColumns(gotArray, n);
        }
        else throw new IllegalArgumentException();


        System.out.println("calculated result");
        printMatrix(result);

        System.out.print("output file path: ");
        System.out.println(outputFilePath);


        ArrayWriter arrayWriter = null;
        try {
            arrayWriter = new ArrayWriter(outputFilePath);
        } catch (IOException e){
            System.err.println("can't open file to output");
            System.exit(-1);
        }

        try {
            arrayWriter.writeMatrix(result);
        } catch (IOException e){
            System.err.println("can't write array in output file");
            System.exit(-1);
        }

    }

    public static void printMatrix(Object[][] arr){
        System.out.print("{");
        for (int row = 0; row < arr.length; row++) {
            if(row != 0) System.out.print(" ");

            System.out.print("{");
            for (int column = 0; column < arr[row].length; column++) {
                Object now = arr[row][column];
                System.out.print(now.toString());
                if(column != arr[row].length -1) System.out.print(",");
                System.out.print(" ");

            }
            System.out.print("}");

            if(row != arr.length -1) System.out.println(",");
        }
        System.out.println("}");
    }
}

