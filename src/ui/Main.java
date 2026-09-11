package ui;

import data.DataProvider;
import model.Car;
import strategy.SortOption;
import com.company.util.FileWriterUtil;

import java.util.*;

import com.company.util.FileWriterUtil;
public class Main {

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
                "Ð¡Ð¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ° Ð¿Ð¾ Ð³Ð¾Ð´Ñ (Ð²ÑÐµ)",
                new SortingManager<>(null),
                Car.BY_YEAR,
                null
        ));
        sortOptions.add(new SortOption(
                "Ð¡Ð¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ° Ð¿Ð¾ Ð³Ð¾Ð´Ñ (ÑÐ¾Ð»ÑÐºÐ¾ ÑÐµÑÐ½ÑÐµ)",
                new SortingManager<>(Car.IGNORE_ODD_YEAR),
                Car.BY_YEAR,
                Car.IGNORE_ODD_YEAR
        ));
        sortOptions.add(new SortOption(
                "Ð¡Ð¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ° Ð¿Ð¾ Ð³Ð¾Ð´Ñ (ÑÐ¾Ð»ÑÐºÐ¾ Ð½ÐµÑÐµÑÐ½ÑÐµ)",
                new SortingManager<>(Car.IGNORE_EVEN_YEAR),
                Car.BY_YEAR,
                Car.IGNORE_EVEN_YEAR
        ));
        sortOptions.add(new SortOption(
                "Ð¡Ð¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ° Ð¿Ð¾ Ð¼Ð¾ÑÐ½Ð¾ÑÑÐ¸ (Ð²ÑÐµ)",
                new SortingManager<>(null),
                Car.BY_POWER,
                null
        ));
        sortOptions.add(new SortOption(
                "Ð¡Ð¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ° Ð¿Ð¾ Ð¼Ð¾Ð´ÐµÐ»Ð¸ (Ð²ÑÐµ)",
                new SortingManager<>(null),
                Car.BY_MODEL,
                null
        ));
    }

    public void run() {
        System.out.println("*** ÐÐ¾Ð±ÑÐ¾ Ð¿Ð¾Ð¶Ð°Ð»Ð¾Ð²Ð°ÑÑ Ð² Ð¿ÑÐ¾Ð³ÑÐ°Ð¼Ð¼Ñ ÑÐ¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ¸ Ð°Ð²ÑÐ¾Ð¼Ð¾Ð±Ð¸Ð»ÐµÐ¹ ***");

        while (true) {
            printMainMenu();
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
                    System.out.println("ÐÑÑÐ¾Ð´ Ð¸Ð· Ð¿ÑÐ¾Ð³ÑÐ°Ð¼Ð¼Ñ. ÐÐ¾ ÑÐ²Ð¸Ð´Ð°Ð½Ð¸Ñ!");
                    return;
                default:
                    System.out.println("ÐÐµÐ²ÐµÑÐ½ÑÐ¹ Ð²ÑÐ±Ð¾Ñ. ÐÐ¾Ð¿ÑÐ¾Ð±ÑÐ¹ÑÐµ ÑÐ½Ð¾Ð²Ð°.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== ÐÐÐÐÐÐÐ ÐÐÐÐ® ===");
        System.out.println("1. ÐÐ°Ð¿Ð¾Ð»Ð½Ð¸ÑÑ Ð¼Ð°ÑÑÐ¸Ð² Ð´Ð°Ð½Ð½ÑÐ¼Ð¸");
        System.out.println("2. ÐÑÐ±ÑÐ°ÑÑ Ð²Ð°ÑÐ¸Ð°Ð½Ñ ÑÐ¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ¸");
        System.out.println("3. ÐÑÐ¿Ð¾Ð»Ð½Ð¸ÑÑ ÑÐ¾ÑÑÐ¸ÑÐ¾Ð²ÐºÑ");
        System.out.println("4. ÐÐ¾ÐºÐ°Ð·Ð°ÑÑ ÑÐµÐºÑÑÐ¸Ð¹ ÑÐ¿Ð¸ÑÐ¾Ðº");
        System.out.println("5. ÐÐ°Ð¿Ð¸ÑÐ°ÑÑ Ð² ÑÐ°Ð¹Ð» (Ð´Ð¾Ð¿. Ð·Ð°Ð´Ð°Ð½Ð¸Ðµ)");
        System.out.println("6. ÐÐ¾Ð´ÑÑÐ¸ÑÐ°ÑÑ Ð²ÑÐ¾Ð¶Ð´ÐµÐ½Ð¸Ñ (Ð´Ð¾Ð¿. Ð·Ð°Ð´Ð°Ð½Ð¸Ðµ)");
        System.out.println("0. ÐÑÑÐ¾Ð´");
        System.out.print("ÐÐ°Ñ Ð²ÑÐ±Ð¾Ñ: ");
    }

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("ÐÑÐ¸Ð±ÐºÐ°: Ð²Ð²ÐµÐ´Ð¸ÑÐµ ÑÐ¸ÑÐ»Ð¾!");
                scanner.nextLine();
                System.out.print("ÐÐ¾Ð¿ÑÐ¾Ð±ÑÐ¹ÑÐµ ÑÐ½Ð¾Ð²Ð°: ");
            }
        }
    }

    private void handleFillData() {
        System.out.println("\n--- ÐÐ°Ð¿Ð¾Ð»Ð½ÐµÐ½Ð¸Ðµ Ð¼Ð°ÑÑÐ¸Ð²Ð° Ð´Ð°Ð½Ð½ÑÐ¼Ð¸ ---");
        System.out.println("1. Ð Ð°Ð½Ð´Ð¾Ð¼");
        System.out.println("2. ÐÐ· ÑÐ°Ð¹Ð»Ð°");
        System.out.println("3. ÐÑÑÑÐ½ÑÑ");
        System.out.print("ÐÑÐ±ÐµÑÐ¸ÑÐµ Ð²Ð°ÑÐ¸Ð°Ð½Ñ: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                System.out.print("ÐÐ²ÐµÐ´Ð¸ÑÐµ ÐºÐ¾Ð»Ð¸ÑÐµÑÑÐ²Ð¾ Ð¼Ð°ÑÐ¸Ð½: ");
                int count = readInt();
                if (count <= 0) {
                    System.out.println("ÐÐ¾Ð»Ð¸ÑÐµÑÑÐ²Ð¾ Ð´Ð¾Ð»Ð¶Ð½Ð¾ Ð±ÑÑÑ Ð±Ð¾Ð»ÑÑÐµ 0!");
                    return;
                }
                cars = DataProvider.generateRandom(count);
                System.out.println("ÐÐ¾Ð±Ð°Ð²Ð»ÐµÐ½Ð¾ " + cars.size() + " Ð¼Ð°ÑÐ¸Ð½.");
                break;

            case 2:
                System.out.print("ÐÐ²ÐµÐ´Ð¸ÑÐµ Ð¸Ð¼Ñ ÑÐ°Ð¹Ð»Ð°: ");
                String fileName = scanner.nextLine();
                cars = DataProvider.readFromFile(fileName);
                System.out.println("ÐÐ°Ð³ÑÑÐ¶ÐµÐ½Ð¾ " + cars.size() + " Ð¼Ð°ÑÐ¸Ð½ Ð¸Ð· ÑÐ°Ð¹Ð»Ð°.");
                break;

            case 3:
                cars = DataProvider.readFromConsole(scanner);
                System.out.println("ÐÐ¾Ð±Ð°Ð²Ð»ÐµÐ½Ð¾ " + cars.size() + " Ð¼Ð°ÑÐ¸Ð½.");
                break;

            default:
                System.out.println("ÐÐµÐ²ÐµÑÐ½ÑÐ¹ Ð²ÑÐ±Ð¾Ñ.");
        }
    }

    private void handleSelectSortOption() {
        System.out.println("\n--- ÐÑÐ±Ð¾Ñ Ð²Ð°ÑÐ¸Ð°Ð½ÑÐ° ÑÐ¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ¸ ---");

        for (int i = 0; i < sortOptions.size(); i++) {
            System.out.println((i + 1) + ". " + sortOptions.get(i).getName());
        }
        System.out.println("0. ÐÑÐ¼ÐµÐ½Ð°");
        System.out.print("ÐÑÐ±ÐµÑÐ¸ÑÐµ Ð²Ð°ÑÐ¸Ð°Ð½Ñ: ");

        int choice = readInt();

        if (choice == 0) {
            System.out.println("ÐÑÐ¼ÐµÐ½Ð° Ð²ÑÐ±Ð¾ÑÐ°.");
            return;
        }
        if (choice < 1 || choice > sortOptions.size()) {
            System.out.println("ÐÐµÐ²ÐµÑÐ½ÑÐ¹ Ð²ÑÐ±Ð¾Ñ.");
            return;
        }

        selectedOption = sortOptions.get(choice - 1);
        System.out.println("ÐÑÐ±ÑÐ°Ð½ Ð²Ð°ÑÐ¸Ð°Ð½Ñ: " + selectedOption.getName());
    }

    private void handleSort() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Ð¡Ð½Ð°ÑÐ°Ð»Ð° Ð·Ð°Ð¿Ð¾Ð»Ð½Ð¸ÑÐµ ÑÐ¿Ð¸ÑÐ¾Ðº!");
            return;
        }
        if (selectedOption == null) {
            System.out.println("Ð¡Ð½Ð°ÑÐ°Ð»Ð° Ð²ÑÐ±ÐµÑÐ¸ÑÐµ Ð²Ð°ÑÐ¸Ð°Ð½Ñ ÑÐ¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ¸ (Ð¿ÑÐ½ÐºÑ 2)!");
            return;
        }

        System.out.println("ÐÑÐ¿Ð¾Ð»Ð½ÑÐµÑÑÑ: " + selectedOption.getName());
        selectedOption.sort(cars);
        System.out.println("Ð¡Ð¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ° Ð²ÑÐ¿Ð¾Ð»Ð½ÐµÐ½Ð°!");
    }

    private void handlePrintCars() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Ð¡Ð¿Ð¸ÑÐ¾Ðº Ð¼Ð°ÑÐ¸Ð½ Ð¿ÑÑÑ. ÐÐ°Ð¿Ð¾Ð»Ð½Ð¸ÑÐµ ÐµÐ³Ð¾!");
            return;
        }

        System.out.println("\n=== Ð¡ÐÐÐ¡ÐÐ ÐÐÐ¨ÐÐ ===");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
        System.out.println("ÐÑÐµÐ³Ð¾: " + cars.size() + " Ð¼Ð°ÑÐ¸Ð½.");
    }

    private void handleWriteToFile() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Ð¡Ð¿Ð¸ÑÐ¾Ðº Ð¿ÑÑÑ. ÐÐµÑÐµÐ³Ð¾ Ð·Ð°Ð¿Ð¸ÑÑÐ²Ð°ÑÑ!");
            return;
        }

        System.out.print("ÐÐ²ÐµÐ´Ð¸ÑÐµ Ð¸Ð¼Ñ ÑÐ°Ð¹Ð»Ð° Ð´Ð»Ñ Ð·Ð°Ð¿Ð¸ÑÐ¸: ");
        String fileName = scanner.nextLine();

        if (fileName == null || fileName.isBlank()) {
            fileName = "sorted_cars.json";
        }

        FileWriterUtil.appendCollectionToJson(fileName, cars, "                     ");

        System.out.println("ÐÐ°Ð¿Ð¸ÑÑ Ð² ÑÐ°Ð¹Ð» : " + fileName);
        System.out.println("ÐÐ°Ð¿Ð¸ÑÐ°Ð½Ð¾ " + cars.size() + " Ð¼Ð°ÑÐ¸Ð½ Ð² ÑÐ°Ð¹Ð».");
    }

    private void handleCountOccurrences() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Ð¡Ð¿Ð¸ÑÐ¾Ðº Ð¿ÑÑÑ. ÐÐµÑÐµÐ³Ð¾ Ð¿Ð¾Ð´ÑÑÐ¸ÑÑÐ²Ð°ÑÑ!");
            return;
        }

        System.out.println("\n--- ÐÐÐÐ¡Ð§ÐÐ¢ ÐÐ¥ÐÐÐÐÐÐÐ ---");
        int targetPower = DataProvider.getPositiveInt(scanner, "ÐÐ²ÐµÐ´Ð¸ÑÐµ Ð¼Ð¾ÑÐ½Ð¾ÑÑÑ Ð´Ð»Ñ Ð¿Ð¾Ð´ÑÑÐµÑÐ°: ");

        int count = 0;
        for (Car car : cars) {
            if (car.getPower() == targetPower) {
                count++;
            }
        }

        if (count == 0) {
            System.out.println("ÐÐ°ÑÐ¸Ð½ Ñ Ð¼Ð¾ÑÐ½Ð¾ÑÑÑÑ " + targetPower + " Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð¾.");
        } else {
            System.out.println("ÐÐ¾Ð»Ð¸ÑÐµÑÑÐ²Ð¾ Ð¼Ð°ÑÐ¸Ð½ Ñ Ð¼Ð¾ÑÐ½Ð¾ÑÑÑÑ " + targetPower + ": " + count);
        }
    }
}