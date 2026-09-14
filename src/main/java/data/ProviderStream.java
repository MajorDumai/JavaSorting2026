package data;

import util.ScannerUtil;
import model.Car;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProviderStream {
    private static final List<DataProvider> DATA_PROVIDERS = List.of(
            new RandomDataProvider(),
            new FromFileDataProvider(),
            new FromConsoleDataProvider()
    );
    private static final int SIZE = DATA_PROVIDERS.size();

    private static Stream<List<Car>> getDataList(Scanner scanner) {
        Supplier<Supplier<List<Car>>> pickStrategy = () -> {
            final int providerId;
            for (int i = 0; i < SIZE; i++) {
                System.out.printf("%d. %s.\n", i + 1, DATA_PROVIDERS.get(i));
            }
            System.out.print("Выберите вариант: ");
            providerId = ScannerUtil.readInt(scanner, 1, SIZE, "Неподходящий выбор") - 1;
            return DATA_PROVIDERS.get(providerId).getDataSupplier(scanner);
        };

        if (scanner == null) {
            throw new NullPointerException("ProviderStream.getDataList(): scanner отсутствует!");
        }
        return Stream.of(pickStrategy)
                .map(Supplier::get)
                .map(Supplier::get);
    }

    public static void addToList(List<Car> carList, Scanner scanner) {
        if (carList == null) {
            throw new NullPointerException("ProviderStream.addToList(): carList отсутствует!");
        }
        getDataList(scanner).flatMap(List::stream).forEach(carList::add);
    }

    public static void overwriteList(List<Car> carList, Scanner scanner) {
        if (carList == null) {
            throw new NullPointerException("ProviderStream.overwriteList(): carList отсутствует!");
        }
        getDataList(scanner).findAny()
                .ifPresent(newCarList -> {
                    carList.clear();
                    carList.addAll(newCarList);
                });
    }
}
