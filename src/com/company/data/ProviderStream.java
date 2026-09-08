package com.company.data;

import java.util.List;

public class ProviderStream<E> {
    public void addToList(List<E> list, DataProvider<E> provider) {
        provider.stream()
                .flatMap(supplier -> supplier.get().stream())
                .forEach(list::add);
    }
}
