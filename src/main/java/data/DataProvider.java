package data;

import model.Car;

import java.util.Scanner;
import java.util.List;
import java.util.function.Supplier;

public abstract class DataProvider {
    abstract Supplier<List<Car>> getDataSupplier(Scanner scanner);
}
