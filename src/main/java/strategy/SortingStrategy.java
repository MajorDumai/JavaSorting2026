package strategy;

import model.Car;
import java.util.List;
import java.util.Comparator;

public interface SortingStrategy<T> {

    void sort(List<T> list, Comparator<? super T> comparator);
}
