package strategy;

public interface Ignorer<T> {
    boolean isIgnorable(T value);
}