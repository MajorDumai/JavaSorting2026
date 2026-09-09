package com.company.data;

import java.util.List;

public interface DataProvider<E> {
    List<E> provide();
    String getOutputName();
}
