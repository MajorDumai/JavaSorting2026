package com.company.data;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProviderStream {
    public static <T> void addToList(List<T> list, List<DataProvider<T>> dataProviders) {
        if (list == null) {
            throw  new NullPointerException("ProviderStream.addToList(): list is null");
        }
        if (dataProviders == null) {
            throw  new NullPointerException("ProviderStream.addToList(): dataProviders is null");
        }
        if (dataProviders.isEmpty()) {
            System.err.println("ProviderStream.addToList(): No dataProviders given, cannot read new data");
            return;
        }

        Supplier<DataProvider<T>> chooseStrategy = () -> {
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
