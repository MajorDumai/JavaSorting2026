package data;

import model.Car;
import list.MyArray;
import util.FileWriterUtil;
import util.ScannerUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataProvider {
    private static final int RND_YEAR_MOD = java.time.Year.now().getValue() - 1899;
    private static final String DESCRIPTION = "description";
    private static final String COUNT = "count";
    private static final String DATA = "data";
    private static final String POWER = "power";
    private static final String MODEL = "model";
    private static final String YEAR = "year";

    public static List<Car> generateRandom(int count) {
        final List<Car> cars = new MyArray<>();
        final String[] models = {"Tesla", "BMW", "Audi", "Mercedes", "Toyota"};

        for (int i = 0; i < count; i++) {
            final int power = 1 + (int) (Math.random() * 999);
            final String model = models[(int) (Math.random() * models.length)];
            final int year = 1900 + (int) (Math.random() * RND_YEAR_MOD);
            cars.add(new Car(power, model, year));
        }
        return cars;
    }

    public static List<Car> readFromFile(String fileName, Scanner scanner) {
        if (fileName == null || fileName.isBlank()) {
            throw new NullPointerException("DataProvider.readFromFile(): Имя файла пустое!");
        }
        if (scanner == null) {
            throw new NullPointerException("DataProvider.readFromFile(): scanner отсутствует!");
        }
        try {
            final List<Map<String, Object>> entries = FileWriterUtil.readAllEntries(fileName);
            for (int i = 0, len = entries.size(); i < len; i++) {
                printListEntry(i + 1, entries.get(i));
            }
            final int selection;
            final List<Car> cars = new MyArray<>();
            final List<Map<String, Object>> data;
            System.out.print("Выберите список из файла: ");
            selection = ScannerUtil.readInt(scanner, 1, entries.size() - 1, "Выбранного списка нет");
            data = (List<Map<String, Object>>) entries.get(selection).get(DATA);
            for (Map<String, Object> jsonCar : data) {
                final int power = (int) jsonCar.get(POWER);
                final String model = (String) jsonCar.get(MODEL);
                final int year = (int) jsonCar.get(YEAR);
                cars.add(new Car(power, model, year));
            }
            return cars;
        } catch (IOException e) {
            System.out.println("Ошибка! Невозможно прочитать данный файл!");
            return List.of();
        }
    }

    public static List<Car> readFromConsole(Scanner scanner) {
        final List<Car> cars = new MyArray<>();
        final int count;
        System.out.print("Сколько машин хотите ввести? ");
        count = ScannerUtil.readInt(scanner);
        for (int i = 0; i < count; i++) {
            final Car car;
            System.out.printf("\n--- Машина %d из %d ---\n", i + 1, count);
            car = ScannerUtil.getCar(scanner);
            System.out.println("Машина добавлена: " + car);
            cars.add(car);
        }

        System.out.println("\nВсего добавлено машин: " + cars.size());
        return cars;
    }

    private static void printListEntry(int index, Map<String, Object> entry) {
        final String description = (String) entry.get(DESCRIPTION);
        final int count = (int) entry.get(COUNT);
        System.out.printf("%d. %s - %d записей\n", index, description, count);
    }
}
