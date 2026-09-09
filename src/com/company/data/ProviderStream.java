package com.company.data;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProviderStream {
    public static <E> void addToList(List<E> list, List<DataProvider<E>> dataProviders) {
        Supplier<DataProvider<E>> chooseStrategy = () -> {
            int providerId;
            final int size;
            dataProviders.addFirst(new CancelAction<>());
            size = dataProviders.size();
            while (true) {
                final Scanner scanner = new Scanner(System.in);
                for (int i = 0; i < size; i++) {
                    System.out.printf("%n%d - %s", i, dataProviders.get(i).getOutputName());
                }
                System.out.print("\nInput action: ");
                try {
                    providerId = scanner.nextInt();
                    if (providerId >= 0 && providerId < size) {
                        return dataProviders.get(providerId);
                    } else {
                        System.out.println("No such action");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("No such action");
                }
            }
        };

        Stream.of(chooseStrategy)
                .map(Supplier::get)
                .map(DataProvider::provide)
                .flatMap(List::stream)
                .forEach(list::add);
    }
}
