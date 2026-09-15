package data;

import list.MyArray;
import model.Car;
import util.ScannerUtil;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class FromConsoleDataProvider extends DataProvider {
    private static final String OUTPUT_NAME = "Ввести с консоли";

    @Override
    public Supplier<List<Car>> getDataSupplier(Scanner scanner) {
        return () -> {
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
        };
    }

    @Override
    public String toString() {
        return  OUTPUT_NAME;
    }
}
