package org.vstar.lab3.Serializers;

import org.vstar.lab3.model.Vehicle;
import org.yaml.snakeyaml.serializer.Serializer;

import java.io.*;
import java.util.List;

public class JavaNativeSerializer{
    public static void serializeVehicleListAsObject(List<Vehicle> vehicles, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(vehicles);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Vehicle> deserializeVehicleListAsObject(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Vehicle>) ois.readObject();
        }
    }
}
