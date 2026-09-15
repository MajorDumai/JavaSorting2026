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
    private static final int RND_POWER_MIN = 1;
    private static final int RND_POWER_RANGE = 1000 - RND_POWER_MIN + 1;
    private static final String[] MODELS = {"Tesla", "BMW", "Audi", "Mercedes", "Toyota"};
    private static final int RND_YEAR_MIN = 1999;
    private static final int RND_YEAR_RANGE = java.time.Year.now().getValue() - RND_YEAR_MIN + 1;
    private static final String DESCRIPTION = "description";
    private static final String COUNT = "count";
    private static final String DATA = "data";
    private static final String POWER = "power";
    private static final String MODEL = "model";
    private static final String YEAR = "year";

    public static List<Car> generateRandom(int count) {
        final List<Car> cars = new MyArray<>();

        for (int i = 0; i < count; i++) {
            final int power = RND_POWER_MIN + (int) (Math.random() * RND_POWER_RANGE);
            final String model = MODELS[(int) (Math.random() * MODELS.length)];
            final int year = RND_YEAR_MIN + (int) (Math.random() * RND_YEAR_RANGE);
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
                final Map<String, Object> entry = entries.get(i);
                final String description = (String) entry.get(DESCRIPTION);
                final int count = (int) entry.get(COUNT);
                System.out.println((i + 1) + ". " + description + " - " + count + " записей");
            }
            final int selection;
            final List<Car> cars = new MyArray<>();
            final List<Map<String, Object>> data;
            System.out.print("Выберите список из файла: ");
            selection = ScannerUtil.readInt(scanner, 1, entries.size(), "Выбранного списка нет") - 1;
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
        if (count == 0) {
            System.out.println("Отмена ввода.");
            return List.of();
        }
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
}
