package count;

import model.Car;
import list.MyArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для AsyncCount")
class AsyncCountTest {

    private Car carCopy;
    private Car carNil;
    private List<Car> cars;
    private final int[] carIds = {0, 1, 2, 3, 4, 0, 0, 2, 4, 1, 3, 1,
            0, 3, 4, 0, 0, 2, 0, 1, 1, 0, 4, 4};
    private final int[] carCounts = {0, 0, 0, 0, 0};
    private final List<Car> carList = new MyArray<>();

    @BeforeEach
    void setUp() {
        Car car1 = Car.builder().setPower(150).setModel("Toyota").setYear(2020).build();
        Car car2 = Car.builder().setPower(200).setModel("BMW").setYear(2018).build();
        Car car3 = Car.builder().setPower(120).setModel("Lada").setYear(2022).build();
        Car car4 = Car.builder().setPower(300).setModel("Audi").setYear(2015).build();
        Car car5 = Car.builder().setPower(180).setModel("Mercedes").setYear(2019).build();
        carCopy = Car.builder().setPower(200).setModel("BMW").setYear(2018).build();
        carNil = Car.builder().setPower(300).setModel("NIL").setYear(2015).build();
        cars = List.of(car1, car2, car3, car4, car5);

        for (int id : carIds) {
            carCounts[id]++;
            carList.add(cars.get(id));
        }
    }

    @Test
    @DisplayName("Подсчёт машин, добавляемых в список")
    void testCountInList() {
        for (int i = 1; i < cars.size(); i++) {
            assertEquals(carCounts[i], AsyncCount.count(carList, cars.get(i)),
                    "Ошибка в подсчёте машин");
        }
    }

    @Test
    @DisplayName("Подсчёт машин, идентичных машине из списка")
    void testCountOutsideList() {
        assertEquals(carCounts[1], AsyncCount.count(carList, carCopy),
                "Ошибка в подсчёте машин");
    }

    @Test
    @DisplayName("Подсчёт машин, не соответствующей ни одной машине из списка")
    void testCountNotInList() {
        assertEquals(0, AsyncCount.count(carList, carNil),
                "Ошибочный подсчёт машин");
    }

    @Test
    @DisplayName("Подсчёт машин из пустого списка")
    void testCountEmptyList() {
        assertEquals(0, AsyncCount.count(new MyArray<>(), carCopy),
                "Ошибочный подсчёт машин");
    }

    @Test
    @DisplayName("Негативный тест: подсчёт из null-списка")
    void testCountNullList() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> AsyncCount.count(null, carCopy)
        );
        assertEquals("AsyncCount.count(): list is null", exception.getMessage());
    }

    @Test
    @DisplayName("Негативный тест: подсчёт null-машины")
    void testCountNullCar() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> AsyncCount.count(carList, null)
        );
        assertEquals("AsyncCount.count(): target is null", exception.getMessage());
    }
}
