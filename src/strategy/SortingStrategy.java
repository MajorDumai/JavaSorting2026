package strategy;

import model.Car;
import java.util.List;
import java.util.Comparator;

public interface SortingStrategy {

    void sort(List<Car> cars, Comparator<Car> comparator);
}
