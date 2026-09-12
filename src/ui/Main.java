package ui;

import com.company.AsyncCount;
import data.DataProvider;
import model.Car;
import strategy.SortOption;
import strategy.SortingManager;
import com.company.util.FileWriterUtil;

import java.util.*;

public class Main {
    private static final String MENU_STR = """
            \n=== ГЛАВНОЕ МЕНЮ ===
            1. Заполнить массив данными
            2. Выбрать вариант сортировки
            3. Выполнить сортировку
            4. Показать текущий список
            5. Записать в файл (доп. задание)
            6. Подсчитать вхождения (доп. задание)
            0. Выход
            Ваш выбор: """;

    private List<Car> cars;
    private List<SortOption> sortOptions;
    private SortOption selectedOption;
    private Scanner scanner;

    public Main() {
        this.cars = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.sortOptions = new ArrayList<>();
        initSortOptions();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void initSortOptions() {
        sortOptions.add(new SortOption(
                "Сортировка по году (все)",
                new SortingManager<>(null),
                Car.BY_YEAR,
                null
        ));
        sortOptions.add(new SortOption(
                "Сортировка по году (только четные)",
                new SortingManager<>(Car.IGNORE_ODD_YEAR),
                Car.BY_YEAR,
                Car.IGNORE_ODD_YEAR
        ));
        sortOptions.add(new SortOption(
                "Сортировка по году (только нечетные)",
                new SortingManager<>(Car.IGNORE_EVEN_YEAR),
                Car.BY_YEAR,
                Car.IGNORE_EVEN_YEAR
        ));
        sortOptions.add(new SortOption(
                "Сортировка по мощности (все)",
                new SortingManager<>(null),
                Car.BY_POWER,
                null
        ));
        sortOptions.add(new SortOption(
                "Сортировка по модели (все)",
                new SortingManager<>(null),
                Car.BY_MODEL,
                null
        ));
    }

    public void run() {
        System.out.println("*** Добро пожаловать в программу сортировки автомобилей ***");

        while (true) {
            System.out.print(MENU_STR);
            int choice = readInt();

            switch (choice) {
                case 1:
                    handleFillData();
                    break;
                case 2:
                    handleSelectSortOption();
                    break;
                case 3:
                    handleSort();
                    break;
                case 4:
                    handlePrintCars();
                    break;
                case 5:
                    handleWriteToFile();
                    break;
                case 6:
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

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число!");
                scanner.nextLine();
                System.out.print("Попробуйте снова: ");
            }
        }
    }

    private void handleFillData() {
        System.out.println("\n--- Заполнение массива данными ---");
        System.out.println("1. Рандом");
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
                cars = DataProvider.readFromFile(fileName, scanner);
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

    private void handleSelectSortOption() {
        System.out.println("\n--- Выбор варианта сортировки ---");

        for (int i = 0; i < sortOptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, sortOptions.get(i).getName());
        }
        System.out.println("0. Отмена");
        System.out.print("Выберите вариант: ");

        int choice = readInt();

        if (choice == 0) {
            System.out.println("Отмена выбора.");
            return;
        }
        if (choice < 1 || choice > sortOptions.size()) {
            System.out.println("Неверный выбор.");
            return;
        }

        selectedOption = sortOptions.get(choice - 1);
        System.out.println("Выбран вариант: " + selectedOption.getName());
    }

    private void handleSort() {
        if (isListEmpty("Заполните его!")) {
            return;
        }
        if (selectedOption == null) {
            System.out.println("Сначала выберите вариант сортировки (пункт 2)!");
            return;
        }

        System.out.println("Выполняется: " + selectedOption.getName());
        selectedOption.sort(cars);
        System.out.println("Сортировка выполнена!");
    }

    private void handlePrintCars() {
        if (isListEmpty("Заполните его!")) {
            return;
        }

        System.out.println("\n=== СПИСОК МАШИН ===");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
        System.out.println("Всего: " + cars.size() + " машин.");
    }

    private void handleWriteToFile() {
        if (isListEmpty("Нечего записывать!")) {
            return;
        }

        System.out.print("Введите имя файла для записи: ");
        String fileName = scanner.nextLine();

        if (fileName == null || fileName.isBlank()) {
            fileName = "sorted_cars.json";
        }

        FileWriterUtil.appendCollectionToJson(fileName, cars, "                     ");

        System.out.println("Запись в файл : " + fileName);
        System.out.println("Записано " + cars.size() + " машин в файл.");
    }

    private void handleCountOccurrences() {
        if (isListEmpty("Нечего подсчитывать!")) {
            return;
        }

        System.out.println("\n--- ПОДСЧЁТ ВХОЖДЕНИЙ ---");
        System.out.println("Введите параметры автомобиля для поиска: ");
        Car targetCar = DataProvider.readCarFromConsole(scanner);

        int count = 0;
        count= AsyncCount.count(cars, targetCar);

        if (count == 0) {
            System.out.println("Машин, совпадающих с " + targetCar + " не найдено.");
        } else {
            System.out.println("Количество машин с мощностью " + targetCar + ": " + count);
        }
    }

    private boolean isListEmpty(String emptyMessageEnd) {
        if (cars == null || cars.isEmpty()) {
            System.out.printf("Список машин пуст. %s%n", emptyMessageEnd);
            return true;
        }
        return false;
    }
}