package com.company;

import java.util.Comparator;
import java.util.Objects;

public class Car {

    private final double power;
    private final String model;
    private final int year;

    public Car(double power, String model, int year) {
        this.power = power;
        this.model = model;
        this.year = year;
    }

    public static final Comparator<Car> BY_YEAR = Comparator.comparingInt(Car::getYear);
    public static final Comparator<Car> BY_POWER = Comparator.comparingDouble(Car::getPower);
    public static final Comparator<Car> BY_MODEL = Comparator.comparing(Car::getModel, String.CASE_INSENSITIVE_ORDER);

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public CarBuilder toBuilder(){
        return new CarBuilder()
                .power(this.power)
                .model(this.model)
                .year(this.year);
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Car car)) return false;
        return Double.compare(power, car.power) == 0
                && year == car.year
                && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    public double getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public static class CarBuilder {
        private double power;
        private String model;
        private int year;

        public CarBuilder power(double power) {
            this.power = power;
            return this;
        }

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            return new Car(power, model, year);
        }
    }
}
