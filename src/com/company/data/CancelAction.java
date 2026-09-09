package com.company.data;

import java.util.List;

public class CancelAction<E> implements DataProvider<E> {
    @Override
    public List<E> provide() {
        System.err.println("Data input has been canceled.");
        return List.of();
    }
}
