package model;

import strategy.Ignorer;

import java.util.Comparator;
import java.util.Objects;

public class Car {

    private final int power;
    private final String model;
    private final int year;

    public Car(int power, String model, int year) {
        this.power = power;
        this.model = model;
        this.year = year;
    }

    public static final Comparator<Car> BY_YEAR = Comparator.comparingInt(Car::getYear);
    public static final Comparator<Car> BY_POWER = Comparator.comparingInt(Car::getPower);
    public static final Comparator<Car> BY_MODEL = Comparator.comparing(Car::getModel, String.CASE_INSENSITIVE_ORDER);

    public static final Ignorer<Car> IGNORE_EVEN_YEAR =(value->value.getYear()%2==0);
    public static final Ignorer<Car> IGNORE_ODD_YEAR =(value->value.getYear()%2!=0);

    public static Builder builder() {
        return new Builder();
    }

    /*public Builder toBuilder(){
        return new Builder()
                .power(this.power)
                .model(this.model)
                .year(this.year);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof Car car)) return false;
        return Double.compare(power, car.power) == 0
                && year == car.year
                && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }


    public static class Builder {
        private int power;
        private String model;
        private int year;

        public Builder setPower(int power) {
            this.power = power;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            return new Car(power, model, year);
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "power=" + power +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}