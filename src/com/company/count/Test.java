package com.company.count;

import java.util.List;

import com.company.Car;
import com.company.MyArray;

public class Test {
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
                    .model("AAA").power(1000).build()};

    private static void test(boolean condition) {
        testCount++;
        if (!condition) {
            throw new RuntimeException(String.format("Test %d failed.", testCount));  
        }
    }
    
    public static void main(String[] args) {
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
        test(AsyncCount.count(new MyArray<>(), CARS[4]) == 0);
        boolean errorCaught = false;
        try {
            AsyncCount.count(null, CARS[4]);
        } catch (NullPointerException e) {
            errorCaught = true;
        } finally {
            test(errorCaught);
        }
        errorCaught = false;
        try {
            AsyncCount.count(carList, null);
        } catch (NullPointerException e) {
            errorCaught = true;
        } finally {
            test(errorCaught);
        }
        System.out.println("All tests complete.");
    }
}
