package org.vstar.lab3.Serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.vstar.lab3.model.Vehicle;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void serializeVehicleToJson(Vehicle vehicle, String filename) throws IOException {
        objectMapper.writeValue(new File(filename), vehicle);
    }

    public static Vehicle deserializeVehicleFromJson(String filename) throws IOException {
        return objectMapper.readValue(new File(filename), Vehicle.class);
    }

    public static void serializeVehicleListToJson(List<Vehicle> vehicleList, String filename) throws IOException {
        objectMapper.writeValue(new File(filename), vehicleList);
    }

    public static List<Vehicle> deserializeVehicleListFromJson(String filename) throws IOException {
        return objectMapper.readValue(new File(filename), new TypeReference<>(){});
    }
}
