package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArrayWriter {

    final BufferedWriter writer;

    /**
     * constructor that open file on specified path
     * @param pathString path to file
     * @throws IOException if file can't be opened and created
     */
    public ArrayWriter(String pathString) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(pathString));
    }

    /**
     * Writes array to opened file
     * @param array array to write
     * @throws IOException if file can't be written
     */
    public void writeMatrix(Object[][] array) throws IOException {
        writer.write(MatrixToString(array));
        writer.close();
    }

    /**
     * convert specified array to string that can be written to a file
     * @param array array that be converted to a string
     * @return string, that looks like
     * "0 1 0 \n
     *  1 0 1 \n
     *  0 1 1 \n"
     */
    private static String MatrixToString(Object[][] array){
        StringBuilder builtString = new StringBuilder();
        for (Object[] row : array) {
            for (Object objNow : row) {
                builtString.append(objNow);
                builtString.append(" ");
            }
            builtString.append("\n");
        }
        return builtString.toString();
    }
}

