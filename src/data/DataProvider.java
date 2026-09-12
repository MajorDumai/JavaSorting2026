package data;

import model.Car;
import com.company.util.FileWriterUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataProvider {
    private static final String DESCRIPTION = "description";
    private static final String COUNT = "count";
    private static final String DATA = "data";
    private static final String POWER = "power";
    private static final String MODEL = "model";
    private static final String YEAR = "year";

    public static List<Car> generateRandom(int count) {
        System.out.println("ВРЕМЕННАЯ ЗАГЛУШКА: generateRandom()");
        List<Car> cars = new ArrayList<>();
        String[] models = {"Tesla", "BMW", "Audi", "Mercedes", "Toyota"};

        for (int i = 0; i < count; i++) {
            int power = 100 + (int) (Math.random() * 200);  // от 100 до 300
            String model = models[(int) (Math.random() * models.length)];
            int year = 2000 + (int) (Math.random() * 25);    // от 2000 до 2025
            cars.add(new Car(power, model, year));
        }
        return cars;
    }

    public static List<Car> readFromFile(String fileName, Scanner scanner) {
        try {
            final List<Map<String, Object>> entries = FileWriterUtil.readAllEntries(fileName);
            for (int i = 0, len = entries.size(); i < len; i++) {
                printEntry(i + 1, entries.get(i));
            }
            while (true) {
                final int selection = getPositiveInt(scanner, "Выберите список из файла: ");
                if (selection >= 1 && selection < entries.size()) {
                    final List<Car> cars = new ArrayList<>();
                    final List<Map<String, Object>> data = (ArrayList<Map<String, Object>>) entries.get(selection).get(DATA);
                    for (Map<String, Object> jsonCar : data) {
                        final int power = (int) jsonCar.get(POWER);
                        final String model = (String) jsonCar.get(MODEL);
                        final int year = (int) jsonCar.get(YEAR);
                        cars.add(new Car(power, model, year));
                    }
                    return cars;
                } else {
                    System.out.println("Ошибка! Выбранного списка нет!");
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка! Невозможно прочитать данный файл!");
            return List.of();
        }
    }

    public static Car readCarFromConsole(Scanner scanner){
        int power = getPositiveInt(scanner, "Введите мощность (лошадиные силы): ");

        String model = getNonEmplyString(scanner, "Введите модель: ");

        int year = getValidYYear(scanner, "Введите год выпуска (1900 - 2026): ");

        return Car.builder().setPower(power).setModel(model).setYear(year).build();
    }

    public static List<Car> readFromConsole(Scanner scanner) {
        List<Car> cars = new ArrayList<>();

        System.out.println("\n*** Ручной ввод машин ***");

        int count = getPositiveInt(scanner, "Сколько машин хотите ввести? ");

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Машина " + (i + 1) + " из " + count + " ---");

            Car car = readCarFromConsole(scanner);
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
                if (value > 0 && value <= 1000) {
                    return value;
                } else if (value <= 0) {
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

    private static void printEntry(int index, Map<String, Object> entry) {
        final String description = (String) entry.get(DESCRIPTION);
        final int count = (int) entry.get(COUNT);
        System.out.printf("%d. %s - %d записей%n", index, description, count);
    }
}
