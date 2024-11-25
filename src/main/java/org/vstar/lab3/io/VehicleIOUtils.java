package org.vstar.lab3.io;

import org.vstar.lab3.model.Owner;
import org.vstar.lab3.model.Vehicle;
import java.io.*;
import java.util.*;

public class VehicleIOUtils {

    public static void writeVehicleList(List<Vehicle> vehicles, String licenseFile, String yearFile) throws IOException {
        // license plates and brands
        try (BufferedWriter bwriter = new BufferedWriter(new FileWriter(licenseFile))) {
            for (Vehicle vehicle : vehicles) {
                String ownerInfo = vehicle.getOwner() != null ? vehicle.getOwner().getFirstName() + "," + vehicle.getOwner().getLastName() : "unknown,unknown";
                bwriter.write(vehicle.getLicensePlate()+","+vehicle.getBrand()+","+ownerInfo);
                bwriter.newLine();
            }
        }

        // years of manufacture
        try (FileOutputStream fos = new FileOutputStream(yearFile)) {
            for (Vehicle vehicle : vehicles) {
                fos.write((vehicle.getYearOfManufacture() + "\n").getBytes());
            }
        }
    }

    public static List<Vehicle> readVehicleList(String licenseFile, String yearFile) throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();

        // license plates and brands
        List<String> licensePlates = new ArrayList<>();
        List<String> brands = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        try (BufferedReader breader = new BufferedReader(new FileReader(licenseFile))) {
            String line;
            while ((line = breader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                licensePlates.add(parts[0]);
                brands.add(parts[1]);
                owners.add(new Owner(parts[2], parts[3]));
            }
        }

        // years of manufacture
        List<Integer> years = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(yearFile)) {
            int byteRead;
            StringBuilder yearString = new StringBuilder();
            while ((byteRead = fis.read()) != -1) {
                char ch = (char) byteRead;
                if (ch=='\n'){
                    years.add(Integer.parseInt(yearString.toString()));
                    yearString.setLength(0);
                }
                else {
                    yearString.append(ch);
                }
            }
            if (!yearString.isEmpty()) { // to add last year
                years.add(Integer.parseInt(yearString.toString()));
            }
        }

        for (int i = 0; i < licensePlates.size(); i++) {
            vehicles.add(new Vehicle(owners.get(i), licensePlates.get(i), brands.get(i), years.get(i)));
        }

        return vehicles;
    }
}
