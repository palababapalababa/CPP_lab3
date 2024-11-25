package org.vstar.lab3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class Vehicle implements Serializable {
    private Owner owner;
    private String licensePlate;
    private String brand;
    @JsonIgnore private transient int yearOfManufacture;
}
