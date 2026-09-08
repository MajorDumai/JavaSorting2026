package model;

public class Car {

        private int power;
        private String model;
        private int year;

        public Car(int power, String model, int year) {
            this.power = power;
            this.model = model;
            this.year = year;
        }

        public int getPower() { return power; }
        public String getModel() { return model; }
        public int getYear() { return year; }

        @Override
        public String toString() {
            return "Car{" +
                    "power=" + power +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    '}';
        }

}
