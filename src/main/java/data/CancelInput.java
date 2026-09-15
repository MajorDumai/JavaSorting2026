package data;

import model.Car;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class CancelInput extends DataProvider {
    private static final String OUTPUT_NAME = "Отмена";

    @Override
    public Supplier<List<Car>> getDataSupplier(Scanner scanner) {
        System.out.println("Отмена ввода.");
        return List::of;
    }

    @Override
    public String toString() {
        return OUTPUT_NAME;
    }
}
