package com.company.data;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProviderStream {
    private static final DataProvider<?>[] PROVIDERS = {
            new CancelAction<>()
    };

    private static final Supplier<DataProvider<?>> CHOOSE_STRATEGY = () -> {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("TODO: action selection instructions");
        final int providerId = scanner.nextInt();
        return PROVIDERS[providerId];
    };

    public static <E> void addToList(List<E> list) {
        Stream.of(CHOOSE_STRATEGY.get())
                .map(DataProvider::provide)
                .flatMap(List::stream)
                .forEach(e -> list.add((E) e));
    }
}
