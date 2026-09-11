package ui;

import comparator.TempComparator;
import data.DataProvider;
import model.Car;
import strategy.SortingStrategy;
import strategy.TempStrategy;

import java.util.*;
import java.util.List;

public class Main {

    private List<Car> cars;
    private SortingStrategy currentStrategy;
    private Comparator<Car> currentComparator;
    private Scanner scanner;

    public Main() {
        this.cars = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run() {
        System.out.println("*** Добро пожаловать в программу сортировки автомобилей ***");

        while (true) {
            printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1:
                    handleFillData();
                    break;
                case 2:
                    handleSelectStrategy();
                    break;
                case 3:
                    handleSelectComparator();
                    break;
                case 4:
                    handleSort();
                    break;
                case 5:
                    handlePrintCars();
                    break;
                case 6:
                    handleWriteToFile();
                    break;
                case 7:
                    handleCountOccurrences();
                    break;
                case 0:
                    System.out.println("Выход из программы. До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Заполнить массив данными");
        System.out.println("2. Выбрать стратегию сортировки");
        System.out.println("3. Выбрать поле для сортировки");
        System.out.println("4. Выполнить сортировку");
        System.out.println("5. Показать текущий список");
        System.out.println("6. Записать в файл (доп. задание)");
        System.out.println("7. Подсчитать вхождения (доп. задание)");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("? Ошибка: введите число!");
                scanner.nextLine();
                System.out.print("Попробуйте снова: ");
            }
        }
    }

    private void handleFillData() {
        System.out.println("\n--- Заполнение массива данными ---");
        System.out.println("1. Случайные данные");
        System.out.println("2. Из файла");
        System.out.println("3. Вручную");
        System.out.print("Выберите вариант: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                System.out.print("Введите количество машин: ");
                int count = readInt();
                if (count <= 0) {
                    System.out.println("Количество должно быть больше 0!");
                    return;
                }
                cars = DataProvider.generateRandom(count);
                System.out.println("Добавлено " + cars.size() + " машин.");
                break;

            case 2:
                System.out.print("Введите имя файла: ");
                String fileName = scanner.nextLine();
                cars = DataProvider.readFromFile(fileName);
                System.out.println("Загружено " + cars.size() + " машин из файла.");
                break;

            case 3:
                cars = DataProvider.readFromConsole(scanner);
                System.out.println("Добавлено " + cars.size() + " машин.");
                break;

            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void handleSelectStrategy() {
        System.out.println("\n--- Выбор стратегии сортировки ---");
        System.out.println("1. Сортировка 1");
        System.out.println("2. Сортировка 2");
        System.out.println("3. Сортировка 3");
        System.out.print("Выберите стратегию: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                currentStrategy = new TempStrategy();
                System.out.println("Выбрана стратегия: сортировка 1");
                break;
            case 2:
                currentStrategy = new TempStrategy();
                System.out.println("Выбрана стратегия: сортировка 2");
                break;
            case 3:
                currentStrategy = new TempStrategy();
                System.out.println("Выбрана стратегия: сортировка 3");
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void handleSelectComparator() {
        System.out.println("\n--- Выбор поля для сортировки ---");
        System.out.println("1. По мощности");
        System.out.println("2. По модели (алфавит)");
        System.out.println("3. По году выпуска");
        System.out.print("Выберите поле: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                currentComparator = new TempComparator();
                System.out.println("Выбран компаратор: по мощности");
                break;
            case 2:
                currentComparator = new TempComparator();
                System.out.println("Выбран компаратор: по модели");
                break;
            case 3:
                currentComparator = new TempComparator();
                System.out.println("Выбран компаратор: по году выпуска");
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void handleSort() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Сначала заполните список машин!");
            return;
        }

        if (currentStrategy == null) {
            System.out.println("Сначала выберите стратегию сортировки!");
            return;
        }

        if (currentComparator == null) {
            System.out.println("Сначала выберите поле для сортировки!");
            return;
        }

        System.out.println("Выполняется сортировка...");
        currentStrategy.sort(cars, currentComparator);
        System.out.println("Сортировка выполнена!");
    }

    private void handlePrintCars() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Список машин пуст. Заполните его!");
            return;
        }

        System.out.println("\n=== СПИСОК МАШИН ===");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
        System.out.println("Всего: " + cars.size() + " машин.");
    }

    private void handleWriteToFile() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Список пуст. Нечего записывать!");
            return;
        }

        System.out.print("Введите имя файла для записи: ");
        String fileName = scanner.nextLine();

        System.out.println("Запись в файл (заглушка): " + fileName);
        System.out.println("(Временная заглушка) Записано " + cars.size() + " машин в файл.");
    }

    private void handleCountOccurrences() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Список пуст. Нечего подсчитывать!");
            return;
        }

        int targetPower = DataProvider.getPositiveInt(scanner, "Введите мощность для подсчета: ");

        int count = 0;
        for (Car car : cars) {
            if (car.getPower() == targetPower) {
                count++;
            }
        }

        System.out.println("Количество машин с мощностью " + targetPower + ": " + count);
    }
}