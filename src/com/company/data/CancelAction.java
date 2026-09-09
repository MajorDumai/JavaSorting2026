package com.company.data;

import java.util.List;

public class CancelAction<T> implements DataProvider<T> {
    private static final String OUTPUT_NAME = "Cancel input";

    @Override
    public List<T> provide() {
        System.err.println("Data input has been cancelled.");
        return List.of();
    }

    @Override
    public String getOutputName() {
        return OUTPUT_NAME;
    }
}
