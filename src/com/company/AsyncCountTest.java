package com.company;

import java.util.ArrayList;
import java.util.List;
import model.Car;

//import com.company.MyArray;

public class AsyncCountTest {
    private static int testCount = 0;
    private static final int[] CAR_IDS = {0, 1, 2, 3,
            0, 0, 2, 1, 2, 3,
            0, 2, 0, 3, 1, 0};
    private static final Car[] CARS = {

            Car.builder().setYear(1999)
                    .setModel("AAA").setPower(1000).build(),
            Car.builder().setYear(2025)
                    .setModel("BBB").setPower(1900).build(),
            Car.builder().setYear(2011)
                    .setModel("CCC").setPower(1290).build(),
            Car.builder().setYear(2020)
                    .setModel("DDD").setPower(1680).build(),
            Car.builder().setYear(1999)
                    .setModel("AAA").setPower(1000).build(),
            Car.builder().setYear(2020)
                    .setModel("UNUSED").setPower(1680).build(),};

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
