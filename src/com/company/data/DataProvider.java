package com.company.data;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class DataProvider<E> {
    abstract List<E> provide();
    public Stream<Supplier<List<E>>> stream() {
        return Stream.of(this::provide);
    }
}
