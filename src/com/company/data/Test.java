package com.company.data;

import com.company.MyArray;

import java.util.List;

public class Test {
    private static final DataProvider<Integer> TEST1 = new DataProvider<Integer>() {
        @Override
        public List<Integer> provide() {
            final List<Integer> list = new MyArray<>();
            for (int i = 0; i < 5; i++) {
                list.add(i);
            }
            return list;
        }

        @Override
        public String getOutputName() {
            return "TEST 1";
        }
    };

    private static final DataProvider<Integer> TEST2 = new DataProvider<Integer>() {
        @Override
        public List<Integer> provide() {
            final List<Integer> list = new MyArray<>();
            for (int i = 16; i > 0; i -= 3) {
                list.add(i);
            }
            return list;
        }

        @Override
        public String getOutputName() {
            return "TEST 2";
        }
    };

    private static final List<Integer> TEST_LIST = List.of(0, 1, 2, 3, 4);
    private static final List<DataProvider<Integer>> TESTS = List.of(TEST1, TEST2);

    private static int testCount = 0;

    private static void test(List<Integer> providerResult) {
        final int size;
        final List<Integer> listA = new MyArray<>(TEST_LIST);
        final List<Integer> listB = new MyArray<>(TEST_LIST);
        testCount++;
        ProviderStream.addToList(listA, new MyArray<>(TESTS));
        listB.addAll(providerResult);
        size = listA.size();
        if (size != listB.size()) {
            throw new RuntimeException(String.format("Test %d failed%n", testCount));
        }
        for (int i = 0; i < size; i++) {
            if (!listA.get(i).equals(listB.get(i))) {
                throw new RuntimeException(String.format("Test %d failed%n", testCount));
            }
        }
    }

    private static <T> void testNullPointer(List<T> list, List<DataProvider<T>> dataProviders) {
        boolean errorCaught = false;
        testCount++;
        try {
            ProviderStream.addToList(list, dataProviders);
        } catch (NullPointerException e) {
            errorCaught = true;
        }
        if (!errorCaught) {
            throw new RuntimeException(String.format("Test %d failed.", testCount));
        }
    }

    public static void main(String[] args) {
        System.out.println("Running ProviderStream tests.");
        for (int i = 0, len = TESTS.size(); i < len; i++) {
            System.out.printf("%nInput %d in the next request", i + 1);
            test(TESTS.get(i).provide());
        }
        System.out.print("\nInput 0 in the next request");
        test(new MyArray<>());
        testNullPointer(null, new MyArray<>(TESTS));
        testNullPointer(new MyArray<>(), null);
        testCount++;
        try {
            ProviderStream.addToList(TEST_LIST, new MyArray<>());
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException(String.format("Test %d failed", testCount));
        }
        System.out.println("All tests complete.");
    }
}
