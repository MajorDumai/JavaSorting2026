package data;

import list.MyArray;
import model.Car;
import util.ScannerUtil;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class RandomDataProvider extends DataProvider {
    private static final String OUTPUT_NAME = "Сгенерировать случайно";
    private static final int RND_POWER_MIN = 1;
    private static final int RND_POWER_RANGE = 1000 - RND_POWER_MIN + 1;
    private static final String[] MODELS = {"Tesla", "BMW", "Audi", "Mercedes", "Toyota"};
    private static final int RND_YEAR_MIN = 1999;
    private static final int RND_YEAR_RANGE = java.time.Year.now().getValue() - RND_YEAR_MIN + 1;

    @Override
    public Supplier<List<Car>> getDataSupplier(Scanner scanner) {
        return () -> {
            final int count;
            final List<Car> cars = new MyArray<>();
            System.out.print("Введите количество машин: ");
            count = ScannerUtil.readInt(scanner);
            for (int i = 0; i < count; i++) {
                final int power = RND_POWER_MIN + (int) (Math.random() * RND_POWER_RANGE);
                final String model = MODELS[(int) (Math.random() * MODELS.length)];
                final int year = RND_YEAR_MIN + (int) (Math.random() * RND_YEAR_RANGE);
                cars.add(Car.builder().setPower(power).setModel(model).setYear(year).build());
            }
            System.out.println("Добавлено " + cars.size() + " машин.");
            return cars;
        };
    }

    @Override
    public String toString() {
        return OUTPUT_NAME;
    }
}
