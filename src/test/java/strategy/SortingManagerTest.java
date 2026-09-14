package strategy;

import model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для SortingManager")
class SortingManagerTest {

    private SortingManager<Car> sorter;
    private Car car1, car2, car3, car4, car5;

    @BeforeEach
    void setUp() {
        sorter = new SortingManager<>(null);

        car1 = Car.builder().setPower(150).setModel("Toyota").setYear(2020).build();
        car2 = Car.builder().setPower(200).setModel("BMW").setYear(2018).build();
        car3 = Car.builder().setPower(120).setModel("Lada").setYear(2022).build();
        car4 = Car.builder().setPower(300).setModel("Audi").setYear(2015).build();
        car5 = Car.builder().setPower(180).setModel("Mercedes").setYear(2019).build();
    }

    // ==================== ПОЗИТИВНЫЕ ТЕСТЫ ====================

    @Test
    @DisplayName("Сортировка по мощности (по возрастанию)")
    void testSortByPower() {
        List<Car> cars = new ArrayList<>(Arrays.asList(car1, car2, car3, car4, car5));
        sorter.sort(cars, Comparator.comparing(Car::getPower));

        for (int i = 1; i < cars.size(); i++) {
            assertTrue(cars.get(i - 1).getPower() <= cars.get(i).getPower(),
                    "Машины должны быть отсортированы по мощности по возрастанию");
        }
    }

    @Test
    @DisplayName("Сортировка по модели (алфавитный порядок)")
    void testSortByModel() {
        List<Car> cars = new ArrayList<>(Arrays.asList(car1, car2, car3, car4, car5));
        sorter.sort(cars, Comparator.comparing(Car::getModel));

        assertEquals("Audi", cars.get(0).getModel());
        assertEquals("BMW", cars.get(1).getModel());
        assertEquals("Lada", cars.get(2).getModel());
        assertEquals("Mercedes", cars.get(3).getModel());
        assertEquals("Toyota", cars.get(4).getModel());
    }

    @Test
    @DisplayName("Сортировка по году (по убыванию)")
    void testSortByYearDescending() {
        List<Car> cars = new ArrayList<>(Arrays.asList(car1, car2, car3, car4, car5));
        sorter.sort(cars, Comparator.comparing(Car::getYear).reversed());

        assertEquals(2022, cars.get(0).getYear());
        assertEquals(2015, cars.get(4).getYear());
    }

    @Test
    @DisplayName("Сортировка с использованием Comparable (без Comparator)")
    void testSortWithComparable() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));
        SortingManager<Integer> intSorter = new SortingManager<>(null);
        intSorter.sort(numbers, null);

        assertEquals(Arrays.asList(1, 2, 3, 5, 8, 9), numbers);
    }

    // ==================== ГРАНИЧНЫЕ СЛУЧАИ ====================

    @Test
    @DisplayName("Сортировка пустого списка")
    void testSortEmptyList() {
        List<Car> empty = new ArrayList<>();
        assertDoesNotThrow(() -> sorter.sort(empty, Comparator.comparing(Car::getPower)));
        assertTrue(empty.isEmpty());
    }

    @Test
    @DisplayName("Сортировка списка с дубликатами")
    void testSortWithDuplicates() {
        Car dup1 = Car.builder().setPower(150).setModel("Toyota").setYear(2020).build();
        Car dup2 = Car.builder().setPower(150).setModel("Toyota").setYear(2020).build();

        List<Car> cars = new ArrayList<>(Arrays.asList(dup1, car3, dup2, car4));
        sorter.sort(cars, Comparator.comparing(Car::getPower));

        assertEquals(120, cars.get(0).getPower());  // car3
        assertEquals(150, cars.get(1).getPower());  // dup1
        assertEquals(150, cars.get(2).getPower());  // dup2
        assertEquals(300, cars.get(3).getPower());  // car4
    }

    @Test
    @DisplayName("Сортировка с компаратором, возвращающим 0 (все элементы равны)")
    void testSortWithZeroComparator() {
        List<Car> cars = new ArrayList<>(Arrays.asList(car1, car2, car3));
        Comparator<Car> zeroComparator = (a, b) -> 0;

        assertDoesNotThrow(() -> sorter.sort(cars, zeroComparator));
        assertEquals(3, cars.size());
    }

    // ==================== НЕГАТИВНЫЕ ТЕСТЫ ====================

    @Test
    @DisplayName("Негативный тест: сортировка null-списка")
    void testSortNullList() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> sorter.sort(null, Comparator.comparing(Car::getPower))
        );
        assertEquals("Sorting manager can't sort a non-existing (null) list.", exception.getMessage());
    }

    @Test
    @DisplayName("Негативный тест: сортировка списка с null-элементами")
    void testSortWithNullElements() {
        List<Car> cars = new ArrayList<>(Arrays.asList(car1, null, car2));

        assertThrows(NullPointerException.class, () -> {
            sorter.sort(cars, Comparator.comparing(Car::getPower));
        });
    }

    // ==================== ТЕСТЫ ДЛЯ ДОП. ЗАДАНИЯ 1 (IGNORER) ====================

    @Test
    @DisplayName("Доп. задание 1: Сортировка с игнорированием чётных значений")
    void testSortWithIgnorer() {
        Ignorer<Car> evenIgnorer = car -> car.getPower() % 2 == 0;
        SortingManager<Car> sorterWithIgnorer = new SortingManager<>(evenIgnorer);

        List<Car> cars = new ArrayList<>(Arrays.asList(
                Car.builder().setPower(150).setModel("A").setYear(2020).build(),  // индекс 0: чётная (игнор)
                Car.builder().setPower(201).setModel("B").setYear(2020).build(),  // индекс 1: нечётная
                Car.builder().setPower(122).setModel("C").setYear(2020).build(),  // индекс 2: чётная (игнор)
                Car.builder().setPower(303).setModel("D").setYear(2020).build()   // индекс 3: нечётная
        ));

        sorterWithIgnorer.sort(cars, Comparator.comparing(Car::getPower));

        List<Car> oddCars = cars.stream()
                .filter(c -> c.getPower() % 2 != 0)
                .toList();

        assertEquals(2, oddCars.size());
        assertEquals(201, oddCars.get(0).getPower());
        assertEquals(303, oddCars.get(1).getPower());

        assertEquals(150, cars.get(0).getPower(), "Чётный элемент должен остаться на индексе 0");
        assertEquals(122, cars.get(2).getPower(), "Чётный элемент должен остаться на индексе 2");
    }

    @Test
    @DisplayName("Доп. задание 1: Сортировка, когда все элементы игнорируются")
    void testSortAllIgnored() {
        Ignorer<Car> ignoreAll = car -> true;
        SortingManager<Car> sorterIgnoreAll = new SortingManager<>(ignoreAll);

        List<Car> cars = new ArrayList<>(Arrays.asList(car1, car2, car3));

        assertDoesNotThrow(() -> sorterIgnoreAll.sort(cars, Comparator.comparing(Car::getPower)));
        assertEquals(car1, cars.get(0));
        assertEquals(car2, cars.get(1));
        assertEquals(car3, cars.get(2));
    }
}