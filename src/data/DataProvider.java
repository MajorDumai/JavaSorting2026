package data;

import model.Car;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

        public static List<Car> generateRandom(int count) {
            System.out.println("ÂĞÅÌÅÍÍÀß ÇÀÃËÓØÊÀ: generateRandom()");
            List<Car> cars = new ArrayList<>();
            String[] models = {"Tesla", "BMW", "Audi", "Mercedes", "Toyota"};

            for (int i = 0; i < count; i++) {
                int power = 100 + (int)(Math.random() * 200);  // îò 100 äî 300
                String model = models[(int)(Math.random() * models.length)];
                int year = 2000 + (int)(Math.random() * 25);    // îò 2000 äî 2025
                cars.add(new Car(power, model, year));
            }
            return cars;
        }

        public static List<Car> readFromFile(String fileName) {
            System.out.println("ÂĞÅÌÅÍÍÀß ÇÀÃËÓØÊÀ: readFromFile() -> " + fileName);
            List<Car> cars = new ArrayList<>();
            cars.add(new Car(150, "Tesla", 2022));
            cars.add(new Car(200, "BMW", 2020));
            return cars;
        }

        public static List<Car> readFromConsole() {
            System.out.println("ÂĞÅÌÅÍÍÀß ÇÀÃËÓØÊÀ: readFromConsole()");
            List<Car> cars = new ArrayList<>();
            cars.add(new Car(120, "Audi", 2021));
            cars.add(new Car(180, "Mercedes", 2019));
            return cars;
        }
}
