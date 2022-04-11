package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static final VerticalParabola VP1 = new VerticalParabola(5, 0, 0.5);
    public static final HorizontalParabola HP1 = new HorizontalParabola(2, 3, -1);
    public static final HorizontalParabola HP2 = new HorizontalParabola(-3, -3, -0.25);
    public static final HorizontalParabola HP3 = new HorizontalParabola(-6, -6, 1);
    public static final Circle C1 = new Circle(-3, -3, 4);

    public static SimpleColor getColor(double x, double y){
        if (VP1.isPointAboveOfParabola(x, y) && !VP1.isPointAboveOfParabola(x, y)) {
            return SimpleColor.ORANGE;
        }
        if (HP3.isPointRightOfParabola(x, y) && HP2.isPointRightOfParabola(x, y) && !C1.isPointInsideCircle(x, y)) {
            return SimpleColor.GREEN;
        }
        if (HP3.isPointRightOfParabola(x, y) && !HP2.isPointRightOfParabola(x, y)) {
            return SimpleColor.YELLOW;
        }
        if (C1.isPointInsideCircle(x, y) && HP1.isPointRightOfParabola(x, y)) {
            return SimpleColor.GRAY;
        }
        if (!HP3.isPointRightOfParabola(x, y) && HP2.isPointRightOfParabola(x, y) && y < -6) {
            return SimpleColor.YELLOW;
        }
        if (!HP1.isPointRightOfParabola(x, y) && !HP2.isPointRightOfParabola(x, y)) {
            return SimpleColor.YELLOW;
        }
        if (!HP2.isPointRightOfParabola(x, y) && !C1.isPointInsideCircle(x, y) && !HP3.isPointRightOfParabola(x, y)) {
            return SimpleColor.BLUE;
        }
        if (!HP1.isPointRightOfParabola(x, y) && HP2.isPointRightOfParabola(x, y) && !C1.isPointInsideCircle(x, y)) {
            return SimpleColor.BLUE;
        }
        if (C1.isPointInsideCircle(x, y) && !HP1.isPointRightOfParabola(x, y)) {
            return SimpleColor.WHITE;
        }
        if (!C1.isPointInsideCircle(x, y) && !HP1.isPointRightOfParabola(x, y) && !HP2.isPointRightOfParabola(x, y)){
            return SimpleColor.WHITE;
        }
        return SimpleColor.ORANGE;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        Scanner scan = new Scanner(System.in);

        printColorForPoint(5, 5);
        printColorForPoint(-1, 1);
        printColorForPoint(-4, 3);
        printColorForPoint(-9, 1);
        printColorForPoint(-9, -4);
        printColorForPoint(-2, -2);
        printColorForPoint(6, -6);
        printColorForPoint(-1, -10);

        System.out.print("Введите значение x = ");
        double x = scan.nextDouble();
        System.out.print("Введите значение y = ");
        double y = scan.nextDouble();

        printColorForPoint(x, y);
    }

    public static void printColorForPoint(double x, double y) {
        System.out.printf("(%.1f, %.1f) -> %s%n", x, y, getColor(x, y));
    }
}