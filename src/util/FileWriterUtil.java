package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileWriterUtil {

    private static final ObjectMapper objectMapper = createObjectMapper();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Создает и настраивает ObjectMapper для работы с JSON
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    /**
     * Записывает коллекцию в JSON файл в режиме добавления данных.
     * Каждая запись оборачивается в объект с метаданными (время, описание, количество).
     *
     * @param filename    путь/имя файла
     * @param collection  коллекция объектов для записи
     * @param description описание сохраняемых данных
     */
    public static <T> void appendCollectionToJson(String filename,
                                                  Collection<T> collection,
                                                  String description) {
        // Валидация входных данных
        if (filename == null || filename.isBlank()) {
            System.err.println("Ошибка: имя файла не может быть пустым.");
            return;
        }

        if (collection == null || collection.isEmpty()) {
            System.out.println("Коллекция пуста, сохранение отменено.");
            return;
        }

        Path filePath = Paths.get(filename);

        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            List<Map<String, Object>> existingData = readExistingData(filePath);

            Map<String, Object> newEntry = createNewEntry(collection, description);
            existingData.add(newEntry);

            writeDataToFile(filePath, existingData);

            System.out.println("Успешно записано " + collection.size() +
                    " элементов в файл: " + filename);

        } catch (IOException e) {
            System.err.println("Ошибка записи в файл '" + filename + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Читает существующие данные из JSON файла
     */
    private static List<Map<String, Object>> readExistingData(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        byte[] fileContent = Files.readAllBytes(filePath);
        if (fileContent.length == 0) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(fileContent, new TypeReference<List<Map<String, Object>>>() {});
    }

    /**
     * Создает новую запись с метаданными
     */
    private static <T> Map<String, Object> createNewEntry(Collection<T> collection, String description) {
        Map<String, Object> entry = new LinkedHashMap<>();
        entry.put("timestamp", LocalDateTime.now().format(FORMATTER));
        entry.put("description", description);
        entry.put("count", collection.size());
        entry.put("data", new ArrayList<>(collection));
        return entry;
    }

    /**
     * Записывает данные в JSON файл
     */
    private static void writeDataToFile(Path filePath, List<Map<String, Object>> data) throws IOException {
        byte[] jsonContent = objectMapper.writeValueAsBytes(data);
        Files.write(filePath, jsonContent);
    }

    /**
     * Читает все записи из JSON файла
     */
    public static List<Map<String, Object>> readAllEntries(String filename) throws IOException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        Path filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        return readExistingData(filePath);
    }
}