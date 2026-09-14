package ui;

public class Test {

        public static void main(String[] args) {
            System.out.println("=== ЗАПУСК ТЕСТА ГЛАВНОГО МЕНЮ ===");
            System.out.println("Проверяем, что программа запускается без ошибок...");

            Main app = new Main();
            app.run();

            System.out.println("Тест пройден, если программа работала без ошибок");
        }
    }
