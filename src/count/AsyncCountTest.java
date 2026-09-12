package count;

import model.Car;
import list.MyArray;

import java.util.List;


public class AsyncCountTest {
    private static int testCount = 0;
    private static final int[] CAR_IDS = {0, 1, 2, 3,
            0, 0, 2, 1, 2, 3,
            0, 2, 0, 3, 1, 0};
    private static final Car[] CARS = {
            new Car(1000, "AAA", 1999),
            new Car(1900, "BBB", 2025),
            new Car(1290, "CCC", 2011),
            new Car(1680, "DDD", 2020),
            new Car(1000, "AAA", 1999),
            new Car(1680, "UNUSED", 2020),
    };

    private static void test(boolean condition) {
        testCount++;
        if (!condition) {
            throw new RuntimeException(String.format("Test %d failed.", testCount));
        }
    }

    private static <E> void testNullPointer(List<E> list, E target) {
        boolean errorCaught = false;
        testCount++;
        try {
            AsyncCount.count(list, target);
        } catch (NullPointerException e) {
            errorCaught = true;
        }
        if (!errorCaught) {
            throw new RuntimeException(String.format("Test %d failed.", testCount));
        }
    }

    public static void main(String[] args) {
        System.out.println("Running AsyncCount tests.");
        final int[] carCounts = {0, 0, 0, 0};
        final List<Car> carList = new MyArray<>();
        for (int id : CAR_IDS) {
            carCounts[id]++;
            carList.add(CARS[id]);
        }
        for (int i = 0; i < 4; i++) {
            test(AsyncCount.count(carList, CARS[i]) == carCounts[i]);
        }
        test(AsyncCount.count(carList, CARS[4]) == carCounts[0]);
        test(AsyncCount.count(carList, CARS[5]) == 0);
        test(AsyncCount.count(new MyArray<>(), CARS[4]) == 0);
        testNullPointer(null, CARS[4]);
        testNullPointer(carList, null);
        System.out.println("All tests complete.");
    }
}
