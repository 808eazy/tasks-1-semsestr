package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите длину гипотенузы: C = ");
        double C = scanner.nextDouble();

        System.out.print("Введите радиус окружности: R = ");
        double R = scanner.nextDouble();

        System.out.print("Введите длина катета: K1 = ");
        double K1 = scanner.nextDouble();

        double K2 = Math.sqrt(C * C - K1 * K1);

        if (C * C == K1*K1 + K2*K2) {

            if (K1 + K2 == 2*R + C) {
                System.out.println("Окружность вписана в прямоугольный треугольник");
            }
            else {
                System.out.println("Окружность не вписана в прямоугольный треугольник");
            }
        }
        else {
            System.out.println("Такой треугольник невозможен");
        }
    }
}