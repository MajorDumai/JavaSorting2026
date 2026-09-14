package ui;

import count.AsyncCount;
import data.ProviderStream;
import util.ScannerUtil;
import model.Car;
import strategy.SortOption;
import strategy.SortingManager;
import util.FileWriterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private final List<Car> cars;
    private final List<SortOption> sortOptions;
    private SortOption selectedOption;
    private final Scanner scanner;

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
                Car.BY_YEAR
        ));
        sortOptions.add(new SortOption(
                "Сортировка по году (только четные)",
                new SortingManager<>(Car.IGNORE_ODD_YEAR),
                Car.BY_YEAR
        ));
        sortOptions.add(new SortOption(
                "Сортировка по году (только нечетные)",
                new SortingManager<>(Car.IGNORE_EVEN_YEAR),
                Car.BY_YEAR
        ));
        sortOptions.add(new SortOption(
                "Сортировка по мощности (все)",
                new SortingManager<>(null),
                Car.BY_POWER
        ));
        sortOptions.add(new SortOption(
                "Сортировка по модели (все)",
                new SortingManager<>(null),
                Car.BY_MODEL
        ));
    }

    public void run() {
        System.out.println("*** Добро пожаловать в программу сортировки автомобилей ***");

        while (true) {
            final int choice;
            System.out.print(MENU_STR);
            choice = ScannerUtil.readInt(scanner, 0, 6, "Неверный выбор");

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
            }
        }
    }

    private void handleFillData() {
        System.out.print("Сохранить предыдущие записи? (Y/N, ДА/НЕТ) ");
        while (true) {
            final String reply = ScannerUtil.readString(scanner, "Ответ пуст");
            switch (reply.toUpperCase()) {
                case "Y":
                case "ДА":
                    ProviderStream.addToList(cars, scanner);
                    return;
                case "N":
                case "НЕТ":
                    ProviderStream.overwriteList(cars, scanner);
                    return;
                default:
                    System.out.println("Неверный ответ!");
            }
        }
    }

    private void handleSelectSortOption() {
        System.out.println("\n--- Выбор варианта сортировки ---");

        for (int i = 0; i < sortOptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, sortOptions.get(i).getName());
        }
        System.out.println("0. Отмена");
        System.out.print("Выберите вариант: ");

        int choice = ScannerUtil.readInt(scanner, 0, sortOptions.size() - 1, "Неверный выбор.");

        if (choice == 0) {
            System.out.println("Отмена выбора.");
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

        System.out.println("\nСПИСОК МАШИН");
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
        String fileName = ScannerUtil.readString(scanner, "Имя файла пусто");

        if (fileName.isBlank()) {
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
        Car targetCar = ScannerUtil.getCar(scanner);

        final int count = AsyncCount.count(cars, targetCar);

        if (count == 0) {
            System.out.println("Машин, совпадающих с " + targetCar + " не найдено.");
        } else {
            System.out.println("Количество машин, совпадающих с " + targetCar + ": " + count);
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