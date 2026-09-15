package data;

import model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FromFileTest {
    private static final String SCANNER_STR = "\n   \nsorted_cars.json\nabc\n-1\n10\n%d\n";

    @Test
    @DisplayName("Тест получения данных из файла")
    void testFromFile() {
        DataProvider dataProvider = new FromFileDataProvider();
        Car car1 = Car.builder().setPower(200).setModel("BMW X5").setYear(2021).build();
        Car car2 = Car.builder().setPower(120).setModel("Lada Vesta").setYear(2022).build();
        Car car3 = Car.builder().setPower(150).setModel("Toyota Camry").setYear(2020).build();
        List<Car> testList = List.of(car1, car2, car3);
        List<Car> carList = dataProvider.getDataSupplier(getScanner(1)).get();
        assertEquals(testList.size(), carList.size(), "Ошибка чтения из файла");
        for (int i = 0, len = testList.size(); i < len; i++) {
            assertEquals(testList.get(i), carList.get(i), "Ошибка чтения из файла");
        }
        car1 = Car.builder().setPower(120).setModel("Lada Vesta").setYear(2022).build();
        car2 = Car.builder().setPower(150).setModel("Toyota Camry").setYear(2020).build();
        car3 = Car.builder().setPower(200).setModel("BMW X5").setYear(2021).build();
        testList = List.of(car1, car2, car3);
        carList = dataProvider.getDataSupplier(getScanner(6)).get();
        assertEquals(testList.size(), carList.size(), "Ошибка чтения из файла");
        for (int i = 0, len = testList.size(); i < len; i++) {
            assertEquals(testList.get(i), carList.get(i), "Ошибка чтения из файла");
        }
    }

    @Test
    @DisplayName("Негативный тест: получение данных с null-scanner")
    void testFromFileNullScanner() {
        DataProvider dataProvider = new FromFileDataProvider();
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> dataProvider.getDataSupplier(null).get()
        );
        assertEquals("FromFileDataProvider.getDataSupplier(): scanner отсутствует!", exception.getMessage());
    }

    Scanner getScanner(int index) {
        return new Scanner(SCANNER_STR.formatted(index)).useDelimiter("\n");
    }
}
