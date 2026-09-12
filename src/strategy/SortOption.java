package strategy;

import model.Car;

import java.util.Comparator;
import java.util.List;

public class SortOption {

    private final String name;
    private final SortingManager<Car> manager;
    private final Comparator<Car> comparator;
    private final Ignorer<Car> ignorer;

    public SortOption(String name,
                      SortingManager<Car> manager,
                      Comparator<Car> comparator,
                      Ignorer<Car> ignorer) {
        this.name = name;
        this.manager = manager;
        this.comparator = comparator;
        this.ignorer = ignorer;
    }

    public String getName() {
        return name;
    }

    public void sort(List<Car> cars) {
        manager.sort(cars, comparator);
    }

    @Override
    public String toString() {
        return name;
    }
}