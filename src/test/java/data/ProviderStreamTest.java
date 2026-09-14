package data;

import count.AsyncCount;
import list.MyArray;
import model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import util.ScannerUtil;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProviderStreamTest {
    final List<Car> carList = new MyArray<>();
    private final String FILENAME = "sorted_cars.json";
    private final int CAR_COUNT = 3;
    private final int POWER = 800;
    private final String MODEL = "TEST CAR";
    private final int YEAR = 1999;
    private final String DATA = String.format("%d\n%s\n%d\n", POWER, MODEL, YEAR);
    private final String FILL_RANDOM = String.format("1\n%d\n", CAR_COUNT);
    private final String FILL_FILE = String.format("2\n%s\n1\n", FILENAME);
    private final String FILL_CONSOLE = String.format("3\n%d\n%s%s%s", CAR_COUNT, DATA, DATA, DATA);

    @Test
    @DisplayName("Тест получения данных из файла")
    void testGetDataFromFile() {
        ProviderStream.addToList(carList, new Scanner(FILL_FILE));
        List<Car> testList = DataProvider.readFromFile(FILENAME, new Scanner("1\n"));
        assertEquals(testList.size(), carList.size(), "Ошибка получения данных из файла");
        for (int i = 0, len = carList.size(); i < len; i++) {
            assertEquals(testList.get(i), carList.get(i), "Ошибка получения данных из файла");
        }
    }

    @Test
    @DisplayName("Тест получения данных с консоли")
    void testGetDataFromConsole() {
        ProviderStream.addToList(carList, new Scanner(FILL_CONSOLE));
        Car car = ScannerUtil.getCar(new Scanner(DATA));
        assertEquals(CAR_COUNT, AsyncCount.count(carList, car), "Ошибка получения данных с консоли");
    }

    @Test
    @DisplayName("Тест получения данных случайной генерацией, проверка добавления и перезаписывания")
    void testGetDataRandom() {
        ProviderStream.addToList(carList, new Scanner(FILL_RANDOM));
        assertEquals(CAR_COUNT, carList.size(), "Ошибка получения случайных данных");
        ProviderStream.addToList(carList, new Scanner(FILL_RANDOM));
        assertEquals(CAR_COUNT, carList.size() * 2, "Ошибка добавления данных");
        ProviderStream.overwriteList(carList, new Scanner(FILL_RANDOM));
        assertEquals(CAR_COUNT, carList.size(), "Ошибка перезаписывания данных");
    }

    @Test
    @DisplayName("Негативный тест: получение данных с null-списком")
    void testGetDataNullList() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ProviderStream.addToList(null, new Scanner(FILL_RANDOM))
        );
        assertEquals("ProviderStream.addToList(): carList отсутствует!", exception.getMessage());
        exception = assertThrows(
                NullPointerException.class,
                () -> ProviderStream.overwriteList(null, new Scanner(FILL_RANDOM))
        );
        assertEquals("ProviderStream.overwriteList(): carList отсутствует!", exception.getMessage());
    }

    @Test
    @DisplayName("Негативный тест: получение данных с null-scanner")
    void testGetDataNullScanner() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ProviderStream.addToList(carList, null)
        );
        assertEquals("ProviderStream.getDataList(): scanner отсутствует!", exception.getMessage());
        exception = assertThrows(
                NullPointerException.class,
                () -> ProviderStream.overwriteList(carList, null)
        );
        assertEquals("ProviderStream.getDataList(): scanner отсутствует!", exception.getMessage());
    }
}
