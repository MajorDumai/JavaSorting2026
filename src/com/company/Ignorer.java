package com.company;

public interface Ignorer<T> {
    boolean isIgnorable(T value);
}