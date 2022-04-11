package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        Scanner scan = new Scanner(System.in);

        System.out.print("Введите значение w = ");
        int w = scan.nextInt(); // ввод ширины фигуры

        if (w < 3) {
            System.out.printf("Введено неверное значение w%n"); // проверяет введенные данные на верность
        } else {
            int PrintTower = PrintTower(w);
        }
    }

    public static int PrintTower(int w) {
        char dash = '-';
        char exclamationPoint = '!';
        int counter = w;// счетчик для строчек с восклицательными знаками

        for (int i = 0; ; i++) {                    // для "-"
            if (i % 2 == 0) {
                for (int j = 0; j < w; j++) {
                    System.out.print(dash);
                    if (j + 1 == w) System.out.println();
                }

            } else {                               // для "!"

                for (int l = 0; l < counter; l++) {
                    if (counter % 2 != 0 && counter != w && l == 0) { // отсев на нечетность строки, отбор первого символа нечетной строки с "!"
                        for (int countOfSpaces = counter; countOfSpaces - w < 0; countOfSpaces++) { // создает " " (пробел) в нечетной строке с "!"
                            System.out.print(" ");
                        }
                    }
                    System.out.print(exclamationPoint);
                    if (l + 1 == counter) {
                        System.out.println();
                    }

                }
                counter--;

            }
            if (counter == -1) System.out.println();
            if (counter == -2) break;
        }
        return counter;
    }
}