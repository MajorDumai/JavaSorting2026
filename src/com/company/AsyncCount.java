package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class AsyncCount {
    private static final int SUBLIST_SIZE = 4;
    private static final int RESERVED_THREADS = 2;

    private static class CountCallable implements Callable<Integer> {
        private final List<Car> carList;
        private final Car targetCar;
        private int partialCount = 0;

        public CountCallable(List<Car> carList, Car targetCar) {
            this.carList = carList;
            this.targetCar = targetCar;
        }

        @Override
        public Integer call() {
            for (Car car : carList) {
                if (targetCar.equals(car)) {
                    partialCount++;
                }
            }
            return partialCount;
        }
    }

    public static int count(List<Car> carList, Car targetCar) {
        assert carList != null && targetCar != null : "AsyncCount.count(): null argument";
        if (carList.isEmpty()) {
            System.err.println("AsyncCount.count(): The carList is empty");
            return 0;
        }
        final int listSize = carList.size();
        final int threadsPreferred = (int) Math.ceil((double) listSize / SUBLIST_SIZE);
        final int threadsAvailable = Math.max(Runtime.getRuntime().availableProcessors() - RESERVED_THREADS, 1);
        final int threadsToUse = Math.min(threadsPreferred, threadsAvailable);
        try (ExecutorService executor = Executors.newFixedThreadPool(threadsToUse)) {
            final List<Integer> starts = new LinkedList<>();
            final List<CountCallable> taskList = new LinkedList<>();
            final List<Future<Integer>> resultList;
            final int totalResult;
            for (int nextStart = 0; nextStart < listSize; nextStart += SUBLIST_SIZE) {
                starts.add(nextStart);
            }
            starts.forEach(start -> {
                final int end = Math.min(start + SUBLIST_SIZE, listSize);
                taskList.add(new CountCallable(carList.subList(start, end), targetCar));
            });
            try {
                resultList = executor.invokeAll(taskList);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            totalResult = resultList.stream().mapToInt(future -> {
                try {
                    return future.get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).sum();
            return totalResult;
        }
    }
}