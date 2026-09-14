package count;

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

    private static class CountCallable<E> implements Callable<Integer> {
        private final List<E> list;
        private final E target;
        private int partialCount = 0;

        public CountCallable(List<E> list, E target) {
            this.list = list;
            this.target = target;
        }

        @Override
        public Integer call() {
            for (E e : list) {
                if (target.equals(e)) {
                    partialCount++;
                }
            }
            return partialCount;
        }
    }

    public static <E> int count(List<E> list, E target) {
        if (target == null) {
            throw new NullPointerException("AsyncCount.count(): list is null");
        }
        if (list == null) {
            throw new NullPointerException("AsyncCount.count(): target is null");
        }
        if (list.isEmpty()) {
            System.err.println("AsyncCount.count(): The list is empty");
            return 0;
        }
        final int listSize = list.size();
        final int threadsPreferred = (int) Math.ceil((double) listSize / SUBLIST_SIZE);
        final int threadsAvailable = Math.max(Runtime.getRuntime().availableProcessors() - RESERVED_THREADS, 1);
        final int threadsToUse = Math.min(threadsPreferred, threadsAvailable);
        try (ExecutorService executor = Executors.newFixedThreadPool(threadsToUse)) {
            final List<Integer> starts = new LinkedList<>();
            final List<CountCallable<E>> taskList = new LinkedList<>();
            final List<Future<Integer>> resultList;
            final int totalResult;
            for (int nextStart = 0; nextStart < listSize; nextStart += SUBLIST_SIZE) {
                starts.add(nextStart);
            }
            starts.forEach(start -> {
                final int end = Math.min(start + SUBLIST_SIZE, listSize);
                taskList.add(new CountCallable<>(list.subList(start, end), target));
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