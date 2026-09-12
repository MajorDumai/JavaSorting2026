package com.company.util;

import model.Car;

import java.util.Scanner;
import java.util.InputMismatchException;

public class ScannerUtil {
    private static final int YEAR = java.time.Year.now().getValue();

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                final int number = scanner.nextInt();
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("Ошибка! Ввод должен быть больше нуля!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Введите число!");
            } finally {
                System.out.print("Попробуйте снова: ");
            }
        }
    }

    public static int readInt(Scanner scanner, int min, int max, String errorMessage) {
        while (true) {
            try {
                final int number = scanner.nextInt();
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.out.printf("Ошибка! %s\n", errorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! введите число!");
            } finally {
                System.out.print("Попробуйте снова: ");
            }
        }
    }

    public static String readString(Scanner scanner, String emptyMessage) {
        while (true) {
            final String string = scanner.next();
            if (string != null && !string.isBlank()) {
                return string;
            } else {
                System.out.printf("Ошибка! %s\n", emptyMessage);
                System.out.print("Попробуйте снова: ");
            }
        }
    }

    public static Car getCar(Scanner scanner) {
        final int power;
        final String model;
        final int year;
        System.out.print("Введите мощность (лошадиные силы, 1 - 1000): ");
        power = readInt(scanner, 1, 1000, "Мощность выходит за допустимые границы (1 - 1000 включительно)");
        System.out.print("Введите модель: ");
        model = readString(scanner, "Модель не может быть пустой");
        System.out.printf("Введите год (1900 - %d): ", YEAR);
        year = readInt(scanner, 1900, YEAR, String.format("Год может быть от 1900 до %d включительно", YEAR));
        return new Car(power, model, year);
    }
}
