package data;

import list.MyArray;
import model.Car;
import util.FileWriterUtil;
import util.ScannerUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class FromFileDataProvider extends DataProvider {
    private static final String OUTPUT_NAME = "Загрузить из файла";
    private static final String DESCRIPTION = "description";
    private static final String COUNT = "count";
    private static final String DATA = "data";
    private static final String POWER = "power";
    private static final String MODEL = "model";
    private static final String YEAR = "year";

    @Override
    public Supplier<List<Car>> getDataSupplier(Scanner scanner) {
        return () -> {
            if (scanner == null) {
                throw new NullPointerException("FromFileDataProvider.getDataSupplier(): scanner отсутствует!");
            }
            final String fileName;
            System.out.print("Введите имя файла: ");
            fileName = ScannerUtil.readString(scanner, "Имя файла пустое");
            try {
                final List<Map<String, Object>> entries = FileWriterUtil.readAllEntries(fileName);
                final int selection;
                final List<Car> cars = new MyArray<>();
                final List<Map<String, Object>> data;
                for (int i = 0, len = entries.size(); i < len; i++) {
                    final Map<String, Object> entry = entries.get(i);
                    final String description = (String) entry.get(DESCRIPTION);
                    final int count = (int) entry.get(COUNT);
                    System.out.println(i + ". " + description + " - " + count + " записей");
                }
                System.out.print("Выберите список из файла: ");
                selection = ScannerUtil.readInt(scanner, 0, entries.size() - 1, "Выбранного списка нет");
                data = (List<Map<String, Object>>) entries.get(selection).get(DATA);
                for (Map<String, Object> jsonCar : data) {
                    final int power = (int) jsonCar.get(POWER);
                    final String model = (String) jsonCar.get(MODEL);
                    final int year = (int) jsonCar.get(YEAR);
                    cars.add(Car.builder().setPower(power).setModel(model).setYear(year).build());
                }
                System.out.println("Загружено " + cars.size() + " машин из файла.");
                return cars;
            } catch (IOException e) {
                System.out.println("Ошибка! Невозможно прочитать данный файл!");
                return List.of();
            }
        };
    }

    @Override
    public String toString() {
        return OUTPUT_NAME;
    }
}
