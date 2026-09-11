package data;

import model.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataProvider {

        public static List<Car> generateRandom(int count) {
            System.out.println("ВРЕМЕННАЯ ЗАГЛУШКА: generateRandom()");
            List<Car> cars = new ArrayList<>();
            String[] models = {"Tesla", "BMW", "Audi", "Mercedes", "Toyota"};

            for (int i = 0; i < count; i++) {
                int power = 100 + (int)(Math.random() * 200);  // от 100 до 300
                String model = models[(int)(Math.random() * models.length)];
                int year = 2000 + (int)(Math.random() * 25);    // от 2000 до 2025
                cars.add(new Car(power, model, year));
            }
            return cars;
        }

        public static List<Car> readFromFile(String fileName) {
            System.out.println("ВРЕМЕННАЯ ЗАГЛУШКА: readFromFile() -> " + fileName);
            List<Car> cars = new ArrayList<>();
            cars.add(new Car(150, "Tesla", 2022));
            cars.add(new Car(200, "BMW", 2020));
            return cars;
        }

        public static List<Car> readFromConsole(Scanner scanner) {
            List<Car> cars = new ArrayList<>();

            System.out.println("\n*** Ручной ввод машин ***");

            int count = getPositiveInt(scanner, "Сколько машин хотите ввести? ");

            for (int i =0; i< count; i++) {
                System.out.println("\n--- Машина " + (i+1) + " из " +count + " ---");

                int power = getPositiveInt(scanner, "Введите мощность (лошадиные силы): ");

                String model = getNonEmplyString(scanner, "Введите модель: ");

                int year = getValidYYear(scanner, "Введите год выпуска (1900 - 2026): ");

                Car car = new Car(power, model, year);
                cars.add(car);

                System.out.println("Машина добавлена: " + car);
            }

            System.out.println("\nВсего добавлено машин: " + cars.size());
            return cars;
        }

        public static int getPositiveInt(Scanner scanner, String prompt) {
            while (true) {
                System.out.println(prompt);
                try {
                    int value = scanner.nextInt();
                    scanner.nextLine();
                    if (value > 0 && value <=1000) {
                        return value;
                    } else if (value <=0 ) {
                        System.out.println("Ошибка! Мощность должна быть больше 0!");
                    } else {
                        System.out.println("Ошибка! Мощность не может быть больше 1000!");
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Ошибка! Введите целое число от 1 до 1000!");
                    scanner.nextLine();
                }
            }
        }

        private static String getNonEmplyString(Scanner scanner, String prompt) {
            while (true) {
                System.out.println(prompt);
                String value = scanner.nextLine().trim();
                if (!value.isEmpty()) {
                    return value;
                } else {
                    System.out.println("Ошибка! Модель не может быть пустой!");
                }
            }
        }

        private static int getValidYYear(Scanner scanner, String prompt) {
            int currentYear = java.time.Year.now().getValue();
            while (true) {
                System.out.println(prompt);
                try {
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    if (year >= 1900 && year <= currentYear) {
                        return year;
                    } else {
                        System.out.println("Ошибка! Год должен быть от 1900 до " + currentYear + "!");
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Ошибка! Введите целое число!");
                    scanner.nextLine();
                }
            }
        }
}
