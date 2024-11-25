package org.vstar.lab3.Serializers;

import org.vstar.lab3.model.Vehicle;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class YamlSerializer {
    private static final Yaml yaml;

    static {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yaml = new Yaml(options);
    }

    public static void serializeVehicleToYaml(Vehicle vehicle, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            if (vehicle.getYearOfManufacture() < 2010){
                Vehicle vehicleCopy = new Vehicle(vehicle.getOwner(), vehicle.getLicensePlate(), null, vehicle.getYearOfManufacture());
                yaml.dump(vehicleCopy, writer);
            }else{
                yaml.dump(vehicle, writer);
            }
        }
    }

    public static Vehicle deserializeVehicleFromYaml(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            return yaml.load(reader);
        }
    }

    public static void serializeVehicleListToYaml(List<Vehicle> vehicles, String filename) throws IOException {
        List<Vehicle> modifiedVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getYearOfManufacture() < 2010) {
                modifiedVehicles.add(new Vehicle(vehicle.getOwner(), vehicle.getLicensePlate(), null, vehicle.getYearOfManufacture()));
            } else {
                modifiedVehicles.add(vehicle);
            }
        }

        try (FileWriter writer = new FileWriter(filename)) {
            yaml.dump(modifiedVehicles, writer);
        }
    }

    public static List<Vehicle> deserializeVehicleListFromYaml(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            return yaml.load(reader);
        }
    }
}
