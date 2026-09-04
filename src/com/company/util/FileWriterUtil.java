package com.company.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class FileWriterUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Записывает коллекцию в файл в режиме добавления (append).
     *
     * @param filename    путь/имя файла
     * @param collection  коллекция объектов для записи
     * @param description описание (заголовок) сохраняемых данных
     */
    public static <T> void appendCollectionToFile(String filename, Collection<T> collection, String description) {
        if (filename == null || filename.trim().isEmpty()) {
            System.err.println("Ошибка: имя файла не может быть пустым.");
            return;
        }
        if (collection == null || collection.isEmpty()) {
            System.out.println("Коллекция пуста, сохранение отменено.");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(filename, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(osw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("Описание: " + description);
            out.println("Время записи: " + LocalDateTime.now().format(FORMATTER));
            out.println("Количество элементов: " + collection.size());

            int index = 1;
            for (T item : collection) {
                String itemStr = (item != null) ? item.toString() : "null";
                out.println(index++ + ". " + itemStr);
            }

            System.out.println("Успешно записано " + collection.size() + " элементов в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл '" + filename + "': " + e.getMessage());
            e.printStackTrace();
        }
    }
}