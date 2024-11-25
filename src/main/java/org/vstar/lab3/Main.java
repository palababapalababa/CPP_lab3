package org.vstar.lab3;

import org.vstar.lab3.Serializers.JavaNativeSerializer;
import org.vstar.lab3.Serializers.JsonSerializer;
import org.vstar.lab3.Serializers.YamlSerializer;
import org.vstar.lab3.io.VehicleIOUtils;
import org.vstar.lab3.model.Owner;
import org.vstar.lab3.model.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Vehicle> vehicles = new ArrayList<>();
    private static final List<Vehicle> originalVehicles = List.of(
            new Vehicle(new Owner("Vladyslav", "Staretskyi"), "BC9876AI", "Ford", 2008),
            new Vehicle(new Owner("Maksym", "Kyrychenko"), "BC1234BC", "Skoda", 2020)
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        resetVehicles();

        while (true) {
            vehicles.forEach(System.out::println);
            System.out.println("\nМеню:");
            System.out.println("1. Серіалізувати список через ObjectStream (.dat)");
            System.out.println("2. Десеріалізувати список через ObjectStream (.dat)");
            System.out.println("3. Серіалізувати список у JSON (.json)");
            System.out.println("4. Десеріалізувати список із JSON файлу (.json)");
            System.out.println("5. Серіалізувати список у YAML (.yaml)");
            System.out.println("6. Десеріалізувати список із YAML файлу (.yaml)");
            System.out.println("7. Записати список у два текстових файли (.txt)");
            System.out.println("8. Завантажити список із двох текстових файлів (.txt)");
            System.out.println("9. Очистити список");
            System.out.println("10. Відновити оригінальні дані у список");
            System.out.println("11. Вихід");
            System.out.print("Оберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Зчитуємо залишок рядка

            if (choice == 11) {
                System.out.println("Вихід із програми.");
                break;
            }

            if (choice >= 1 && choice <= 6) {
                System.out.print("Введіть назву файлу (без розширення): ");
                String filename = formatFilename(scanner.nextLine().trim(), getFileExtension(choice));

                switch (choice) {
                    case 1 -> handleSerialization(filename, ".dat", JavaNativeSerializer::serializeVehicleListAsObject);
                    case 2 -> handleDeserialization(filename, ".dat", JavaNativeSerializer::deserializeVehicleListAsObject);
                    case 3 -> handleSerialization(filename, ".json", JsonSerializer::serializeVehicleListToJson);
                    case 4 -> handleDeserialization(filename, ".json", JsonSerializer::deserializeVehicleListFromJson);
                    case 5 -> handleSerialization(filename, ".yaml", YamlSerializer::serializeVehicleListToYaml);
                    case 6 -> handleDeserialization(filename, ".yaml", YamlSerializer::deserializeVehicleListFromYaml);
                }
            } else if (choice == 7 || choice == 8) {
                System.out.print("Введіть назву першого файлу (без розширення): ");
                String firstFile = formatFilename(scanner.nextLine().trim(), ".txt");

                System.out.print("Введіть назву другого файлу (без розширення): ");
                String secondFile = formatFilename(scanner.nextLine().trim(), ".txt");

                if (choice == 7) {
                    writeVehicleListToTextFiles(firstFile, secondFile);
                } else {
                    readVehicleListFromTextFiles(firstFile, secondFile);
                }
            } else if (choice == 9) {
                vehicles.clear();
                System.out.println("Список очищено.");
            } else if (choice == 10) {
                resetVehicles();
                System.out.println("Список відновлено до початкових даних.");
            } else {
                System.out.println("Невірна опція. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }

    private static String getFileExtension(int choice) {
        return switch (choice) {
            case 1, 2 -> ".dat";
            case 3, 4 -> ".json";
            case 5, 6 -> ".yaml";
            default -> "";
        };
    }

    private static void handleSerialization(String filename, String extension, SerializationHandler handler) {
        filename = formatFilename(filename, extension);
        try {
            handler.serialize(vehicles, filename);
            System.out.println("Список серіалізовано у файл: " + filename);
        } catch (IOException e) {
            System.err.println("Сталася помилка під час серіалізації: " + e.getMessage());
        }
    }

    private static void handleDeserialization(String filename, String extension, DeserializationHandler handler) {
        filename = formatFilename(filename, extension);
        try {
            vehicles = handler.deserialize(filename);
            System.out.println("Список десеріалізовано з файлу: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Сталася помилка під час десеріалізації: " + e.getMessage());
        }
    }

    private static void writeVehicleListToTextFiles(String licenseFile, String yearFile) {
        try {
            VehicleIOUtils.writeVehicleList(vehicles, licenseFile, yearFile);
            System.out.println("Список записано у файли: " + licenseFile + " та " + yearFile);
        } catch (IOException e) {
            System.err.println("Сталася помилка під час запису: " + e.getMessage());
        }
    }

    private static void readVehicleListFromTextFiles(String licenseFile, String yearFile) {
        try {
            vehicles = VehicleIOUtils.readVehicleList(licenseFile, yearFile);
            System.out.println("Список завантажено з файлів: " + licenseFile + " та " + yearFile);
        } catch (IOException e) {
            System.err.println("Сталася помилка під час читання: " + e.getMessage());
        }
    }

    private static String formatFilename(String filename, String extension) {
        if (filename.endsWith(extension)) {
            return filename;
        }
        return filename + extension;
    }

    private static void resetVehicles() {
        vehicles = new ArrayList<>(originalVehicles);
    }

    @FunctionalInterface
    private interface SerializationHandler {
        void serialize(List<Vehicle> vehicles, String filename) throws IOException;
    }

    @FunctionalInterface
    private interface DeserializationHandler {
        List<Vehicle> deserialize(String filename) throws IOException, ClassNotFoundException;
    }
}