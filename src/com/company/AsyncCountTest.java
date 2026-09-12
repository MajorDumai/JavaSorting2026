package com.company;

import java.util.ArrayList;
import java.util.List;

//import com.company.MyArray;

public class AsyncCountTest {
    private static int testCount = 0;
    private static final int[] CAR_IDS = {0, 1, 2, 3,
            0, 0, 2, 1, 2, 3,
            0, 2, 0, 3, 1, 0};
    private static final Car[] CARS = {
            Car.builder().year(1999)
                    .model("AAA").power(1000).build(),
            Car.builder().year(2025)
                    .model("BBB").power(1900).build(),
            Car.builder().year(2011)
                    .model("CCC").power(1290).build(),
            Car.builder().year(2020)
                    .model("DDD").power(1680).build(),
            Car.builder().year(1999)
                    .model("AAA").power(1000).build(),
            Car.builder().year(2020)
                    .model("UNUSED").power(1680).build(),};

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
        final List<Car> carList = new ArrayList<>();
        for (int id : CAR_IDS) {
            carCounts[id]++;
            carList.add(CARS[id]);
        }
        for (int i = 0; i < 4; i++) {
            test(AsyncCount.count(carList, CARS[i]) == carCounts[i]);
        }
        test(AsyncCount.count(carList, CARS[4]) == carCounts[0]);
        test(AsyncCount.count(carList, CARS[5]) == 0);
        test(AsyncCount.count(new ArrayList<>(), CARS[4]) == 0);
        testNullPointer(null, CARS[4]);
        testNullPointer(carList, null);
        System.out.println("All tests complete.");
    }
}
