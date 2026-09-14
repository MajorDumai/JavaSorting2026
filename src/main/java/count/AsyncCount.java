package count;

import list.MyArray;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinPool;
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
        if (list == null) {
            throw new NullPointerException("AsyncCount.count(): list is null");
        }
        if (target == null) {
            throw new NullPointerException("AsyncCount.count(): target is null");
        }
        if (list.isEmpty()) {
            System.out.println("Список не содержит записей.");
            return 0;
        }
        final int listSize = list.size();
        final int threadsPreferred = (int) Math.ceil((double) listSize / SUBLIST_SIZE);
        final int threadsAvailable = Math.max(Runtime.getRuntime().availableProcessors() - RESERVED_THREADS, 1);
        final int threadsToUse = Math.min(threadsPreferred, threadsAvailable);
        try (ForkJoinPool executor = new ForkJoinPool(threadsToUse)) {
            final List<Integer> starts = new MyArray<>();
            final List<ForkJoinTask<Integer>> resultList = new MyArray<>();
            final int totalResult;
            for (int nextStart = 0; nextStart < listSize; nextStart += SUBLIST_SIZE) {
                starts.add(nextStart);
            }
            starts.forEach(start -> {
                final int end = Math.min(start + SUBLIST_SIZE, listSize);
                resultList.add(executor.submit(new CountCallable<>(list.subList(start, end), target)));
            });
            totalResult = resultList.stream().parallel().mapToInt(future -> {
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