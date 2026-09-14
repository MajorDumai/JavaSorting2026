package util;

import model.Car;

import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScannerUtilTest {
    @Test
    @DisplayName("Тест получения позитивного числа")
    void testReadInt() {
        String testInput = "abc\n\n-6\n  \n5\n";
        Scanner scanner = new Scanner(testInput).useDelimiter("\n");
        assertEquals(5, ScannerUtil.readInt(scanner),
                "Ошибка чтения числа");
        testInput = "\n0\n";
        scanner = new Scanner(testInput).useDelimiter("\n");
        assertEquals(0, ScannerUtil.readInt(scanner),
                "Ошибка чтения числа");
    }

    @Test
    @DisplayName("Тест получения числа из промежутка")
    void testReadIntRange() {
        String testInput = "abc\n\n0\n-6\n  \n5\n11\n7\n";
        Scanner scanner = new Scanner(testInput).useDelimiter("\n");
        assertEquals(7, ScannerUtil.readInt(scanner, 7, 10, "")
                , "Ошибка чтения числа из отрезка");
        scanner = new Scanner(testInput).useDelimiter("\n");
        assertEquals(-6, ScannerUtil.readInt(scanner, -12, -6, "")
                , "Ошибка чтения отрицательного числа из отрезка");
    }

    @Test
    @DisplayName("Тест получения не пустой строки")
    void testReadString() {
        String testInput = "\n \n   \nabc\n";
        Scanner s = new Scanner(testInput).useDelimiter("\n");
        assertEquals("abc", ScannerUtil.readString(s, "")
                , "Ошибка чтения строки");
    }

    @Test
    @DisplayName("Тест получения машины")
    void testGetCar() {
        int power = 800;
        String model = "TEST CAR";
        int year = 1999;
        Car car = Car.builder().setPower(power).setModel(model).setYear(year).build();
        String testInput = "\nabc\n-50\n2000\n%d\n   \n%s\n1800\n2525\nyear\n%d\n";
        Scanner s = new Scanner(String.format(testInput
                , power, model, year)).useDelimiter("\n");
        assertEquals(car, ScannerUtil.getCar(s)
                , "Ошибка чтения машины");
    }

    @Test
    @DisplayName("Негативный тест: запрос с null-scanner")
    void testNullScanner() {
        String error = "ScannerUtil.%s(): scanner is null";
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ScannerUtil.readInt(null)
        );
        assertEquals(error.formatted("readInt"), exception.getMessage());
        exception = assertThrows(
                NullPointerException.class,
                () -> ScannerUtil.readInt(null, 0, 10, "")
        );
        assertEquals(error.formatted("readInt"), exception.getMessage());
        exception = assertThrows(
                NullPointerException.class,
                () -> ScannerUtil.readString(null, "")
        );
        assertEquals(error.formatted("readString"), exception.getMessage());
        exception = assertThrows(
                NullPointerException.class,
                () -> ScannerUtil.getCar(null)
        );
        assertEquals(error.formatted("getCar"), exception.getMessage());
    }

    @Test
    @DisplayName("Негативный тест: ошибка границ в запросе числа из отрезка")
    void testReadIntBoundaryError() {
        String error = "ScannerUtil.readInt(): minimal boundary is equal or higher than maximum";
        String testInput = "5\n";
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> ScannerUtil.readInt(new Scanner(testInput).useDelimiter("\n"), 10, 0, "")
        );
		assertEquals(error, exception.getMessage());
        exception = assertThrows(
                RuntimeException.class,
                () -> ScannerUtil.readInt(new Scanner(testInput).useDelimiter("\n"), 5, 5, "")
        );
		assertEquals(error, exception.getMessage());
    }
}
