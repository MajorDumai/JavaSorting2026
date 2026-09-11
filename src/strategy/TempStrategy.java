package strategy;

import model.Car;
import java.util.List;
import java.util.Comparator;

public class TempStrategy implements SortingStrategy{

        @Override
        public void sort(List<Car> cars, Comparator<Car> comparator) {
            System.out.println("ВРЕМЕННАЯ ЗАГЛУШКА: сортировка НЕ выполняется");
            System.out.println("   (Реальная сортировка появится позже)");

            System.out.println("   Текущий список (без сортировки):");
            for (Car car : cars) {
                System.out.println("   - " + car);
            }
        }
}
