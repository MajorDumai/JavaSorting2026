package com.company.test;

import model.Car;
import strategy.SortingManager;
import com.company.util.FileWriterUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FileWriterTest {
    public static void main(String[] args) {
        System.out.println("Тестирование записи в JSON\n");

        Car car1 = Car.builder()
                .setPower(150)
                .setModel("Toyota Camry")
                .setYear(2020)
                .build();

        Car car2 = Car.builder()
                .setPower(200)
                .setModel("BMW X5")
                .setYear(2021)
                .build();

        Car car3 = Car.builder()
                .setPower(120)
                .setModel("Lada Vesta")
                .setYear(2022)
                .build();

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        String fileName = "sorted_cars.json";

        System.out.println("Тест 1: Запись отсортированных по модели автомобилей");
        SortingManager<Car> sorter = new SortingManager<>(null);
        sorter.sort(cars, Comparator.comparing(Car::getModel));
        FileWriterUtil.appendCollectionToJson(fileName, cars, "Сортировка по модели");

        System.out.println("\nТест 2: Запись отсортированных по году автомобилей");
        sorter.sort(cars, Comparator.comparing(Car::getYear));
        FileWriterUtil.appendCollectionToJson(fileName, cars, "Сортировка по году");

        System.out.println("\nТест 3: Запись отсортированных по мощности автомобилей");
        sorter.sort(cars, Comparator.comparing(Car::getPower));
        FileWriterUtil.appendCollectionToJson(fileName, cars, "Сортировка по мощности");

        System.out.println("\nЧтение всех записей из файла");
        try {
            List<Map<String, Object>> allEntries = FileWriterUtil.readAllEntries(fileName);
            System.out.println("Всего записей в файле: " + allEntries.size());

            for (int i = 0; i < allEntries.size(); i++) {
                Map<String, Object> entry = allEntries.get(i);
                System.out.println("\nЗапись #" + (i + 1));
                System.out.println("  Время: " + entry.get("timestamp"));
                System.out.println("  Описание: " + entry.get("description"));
                System.out.println("  Количество: " + entry.get("count"));
                System.out.println("  Данные: " + entry.get("data"));
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        System.out.println("\nТест завершен!");
        System.out.println("Откройте файл " + fileName + " для просмотра JSON данных");
    }
}