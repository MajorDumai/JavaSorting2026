package data;

import model.Car;

import java.util.Optional;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProviderStream {
    private static final String MENU_STR = """
            \n--- Заполнение массива данными ---
            1. Рандом
            2. Из файла
            3. Вручную
            Выберите вариант: """;

    private static Stream<List<Car>> getDataList(Scanner scanner) {
        final Supplier<List<Car>> readData = () -> {
            while (true) {
                try {
                    final int selection;
                    final List<Car> cars;
                    System.out.print(MENU_STR);
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1:
                            System.out.print("Введите количество машин: ");
                            final int count = scanner.nextInt();
                            if (count <= 0) {
                                System.out.println("Количество должно быть больше 0!");
                                return List.of();
                            } else {
                                cars = DataProvider.generateRandom(count);
                                System.out.println("Добавлено " + cars.size() + " машин.");
                                return cars;
                            }
                        case 2:
                            System.out.print("Введите имя файла: ");
                            final String fileName = scanner.next();
                            cars = DataProvider.readFromFile(fileName, scanner);
                            System.out.println("Загружено " + cars.size() + " машин из файла.");
                            return cars;
                        case 3:
                            cars = DataProvider.readFromConsole(scanner);
                            System.out.println("Добавлено " + cars.size() + " машин.");
                            return cars;
                        default:
                            System.out.println("Ошибка: нет данного выбора!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка: введите число!");
                }
            }
        };

        return Stream.of(readData)
                .map(Supplier::get);
    }

    public static void addToList(List<Car> carList, Scanner scanner) {
        getDataList(scanner).flatMap(List::stream).forEach(carList::add);
    }

    public static void overwriteList(List<Car> carList, Scanner scanner) {
        getDataList(scanner).findAny()
                .ifPresent(newCarList -> {
                    carList.clear();
                    carList.addAll(newCarList);
                });
    }
}
