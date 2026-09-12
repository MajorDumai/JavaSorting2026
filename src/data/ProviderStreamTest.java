package data;

import list.MyArray;
import model.Car;

import java.util.List;
import java.util.Scanner;

public class ProviderStreamTest {
    private static final String FILENAME = "sorted_cars.json";
    private static final int CAR_COUNT = 3;
    private static final int POWER = 800;
    private static final String MODEL = "TEST CAR";
    private static final int YEAR = 1999;
    private static final String DATA = String.format("%d\n%s\n%d\n", POWER, MODEL, YEAR);
    private static final String FILL_FILE = String.format("2\n%s\n1\n", FILENAME);
    private static final String FILL_CONSOLE = String.format("3\n%d\n%s%s%s", CAR_COUNT, DATA, DATA, DATA);
    private static final String FILL_RANDOM = String.format("1\n%d\n", CAR_COUNT);
    private static final String TEST_STR = String.format("%s%s%s", FILL_FILE, FILL_CONSOLE, FILL_RANDOM);

    private static int testCount = 0;

    private static void test(int count, int target) {
        testCount++;
        if (count != target) {
            throw new RuntimeException(String.format("Test %d failed\n", testCount));
        }
    }

    private static void test(String filename, Scanner scanner) {
        testCount++;
        try {
            DataProvider.readFromFile(filename, scanner);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(String.format("Test %d failed", testCount));
    }

    private static void test(List<Car> carList, Scanner scanner) {
        testCount++;
        try {
            ProviderStream.addToList(carList, scanner);
        } catch (NullPointerException e1) {
            try {
                ProviderStream.overwriteList(carList, scanner);
            } catch (NullPointerException e2) {
                return;
            }
        }
        throw new RuntimeException(String.format("Test %d failed", testCount));
    }

    private static void test(List<Car> carList, List<Car> targetList) {
        testCount++;
        final int size = carList.size();
        if (size != targetList.size()) {
            throw new RuntimeException(String.format("Test %d failed\n", testCount));
        }
        for (int i = 0; i < size; i++) {
            if (!carList.get(i).equals(targetList.get(i))) {
                throw new RuntimeException(String.format("Test %d failed\n", testCount));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Running ProviderStream tests.");
        final List<Car> carList = new MyArray<>();
        final List<Car> testList = DataProvider.readFromFile(FILENAME, new Scanner("1\n"));
        final Scanner scanner = new Scanner(String.format(TEST_STR)).useDelimiter("\n");
        ProviderStream.addToList(carList, scanner);
        test(carList.size(), testList.size());
        test(carList, testList);
        ProviderStream.overwriteList(carList, scanner);
        testList.clear();
        for (int i = 0; i < CAR_COUNT; i++) {
            testList.add(new Car(POWER, MODEL, YEAR));
        }
        test(carList.size(), CAR_COUNT);
        test(carList, testList);
        ProviderStream.addToList(carList, scanner);
        test(carList.size(), CAR_COUNT * 2);
        test(FILENAME, null);
        test(carList, (Scanner) null);
        test((String) null, scanner);
        test((List<Car>) null, scanner);
        System.out.println("All tests complete.");
    }
}
