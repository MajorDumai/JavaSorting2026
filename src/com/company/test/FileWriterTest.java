package com.company.test;

import java.util.ArrayList;
import java.util.Comparator;
import com.company.util.FileWriterUtil;

public class FileWriterTest {
    public static void main(String[] args) {
        System.out.println("Тестирование записи в файл");

        Car car1 = new Car.Builder().setPower(150).setModel("Toyota Camry").setYear(2020).build();
        Car car2 = new Car.Builder().setPower(200).setModel("BMW X5").setYear(2021).build();
        Car car3 = new Car.Builder().setPower(120).setModel("Lada Vesta").setYear(2022).build();

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        ThatSortingThing<Car> sorter = new ThatSortingThing<>(null);
        sorter.mySort(cars, Comparator.comparing(Car::getModel));

        String fileName = "sorted_cars.txt";

        FileWriterUtil.appendCollectionToFile(fileName, cars, "Первая сортировка (по модели)");

        sorter.mySort(cars, Comparator.comparing(Car::getYear));
        FileWriterUtil.appendCollectionToFile(fileName, cars, "Вторая сортировка (по году)");

        System.out.println("Тест завершен! Откройте файл " + fileName + " и проверьте, что там две записи.");
    }
}