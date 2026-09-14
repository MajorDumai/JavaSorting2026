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
        Scanner s = new Scanner("abc\n\n0\n-6\n  \n5\n").useDelimiter("\n");
        assertEquals(5, ScannerUtil.readInt(s),
                "Ошибка чтения числа");
    }

    @Test
    @DisplayName("Тест получения числа из промежутка")
    void testReadIntRange() {
        Scanner s = new Scanner("abc\n\n0\n-6\n  \n5\n11\n7\n").useDelimiter("\n");
        assertEquals(7, ScannerUtil.readInt(s, 7, 10, "")
                , "Ошибка чтения числа из отрезка");
        s = new Scanner("abc\n\n0\n-6\n  \n5\n11\n7\n").useDelimiter("\n");
        assertEquals(-6, ScannerUtil.readInt(s, -12, -6, "")
                , "Ошибка чтения отрицательного числа из отрезка");
    }

    @Test
    @DisplayName("Тест получения не пустой строки")
    void testReadString() {
        Scanner s = new Scanner("\n \n   \nabc\n").useDelimiter("\n");
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
        Scanner s = new Scanner(String.format("\nabc\n-50\n2000\n%d\n   \n%s\n1800\n2525\nyear\n%d\n"
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
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> ScannerUtil.readInt(new Scanner("5\n").useDelimiter("\n"), 10, 0, "")
        );
		assertEquals(error, exception.getMessage());
        exception = assertThrows(
                RuntimeException.class,
                () -> ScannerUtil.readInt(new Scanner("5\n").useDelimiter("\n"), 5, 5, "")
        );
		assertEquals(error, exception.getMessage());
    }
}
