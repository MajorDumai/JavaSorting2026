package comparator;

import model.Car;
import java.util.Comparator;

public class TempComparator implements Comparator<Car>{

        @Override
        public int compare(Car c1, Car c2) {
            int powerCompare = Integer.compare(c1.getPower(), c2.getPower());
            if (powerCompare != 0) {
                return powerCompare;
            }
            return Integer.compare(c1.getYear(), c2.getYear());
        }
}
