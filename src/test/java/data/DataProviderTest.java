package data;

import model.Car;
import list.MyArray;
import util.FileWriterUtil;
import util.ScannerUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataProviderTest {
    @Test
    @DisplayName("Тест получения данных из файла")
    void testReadFromFile() {
        Car car1 = Car.builder().setPower(200).setModel("BMW X5").setYear(2021).build();
        Car car2 = Car.builder().setPower(120).setModel("Lada Vesta").setYear(2022).build();
        Car car3 = Car.builder().setPower(150).setModel("Toyota Camry").setYear(2020).build();
        List<Car> testList = List.of(car1, car2, car3);
        List<Car> carList = DataProvider.readFromFile("sorted_cars.json", new Scanner("3\n"));
        assertEquals(testList.size(), carList.size(), "Ошибка чтения из файла");
        for (int i = 0, len = testList.size(); i < len; i++) {
            assertEquals(testList.get(i), carList.get(i), "Ошибка чтения из файла");
        }
    }

    @Test
    @DisplayName("Негативный тест: получение данных с null-строкой имени файла")
    void testReadFromFileNullFileName() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> DataProvider.readFromFile(null, new Scanner("3\n"))
        );
        assertEquals("DataProvider.readFromFile(): Имя файла пустое!", exception.getMessage());
        exception = assertThrows(
                NullPointerException.class,
                () -> DataProvider.readFromFile("  \n  \n ", new Scanner("3\n"))
        );
        assertEquals("DataProvider.readFromFile(): Имя файла пустое!", exception.getMessage());
    }

    @Test
    @DisplayName("Негативный тест: получение данных с null-scanner")
    void testReadFromFileNullScanner() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> DataProvider.readFromFile("sorted_cars.json", null)
        );
        assertEquals("DataProvider.readFromFile(): scanner отсутствует!", exception.getMessage());
    }
}
