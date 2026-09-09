package com.company.data;

import java.util.List;

public interface DataProvider<T> {
    List<T> provide();
    String getOutputName();
}
