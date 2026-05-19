package com.david.projects.springboot.delivery_routes.dto.request;

import com.david.projects.springboot.delivery_routes.entities.VehicleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateVehicleRequest {

    @NotBlank(message = "{NotBlank.vehicle.plate}")
    @Size(min = 6, max = 6, message = "La patente debe tener 6 caracteres")
    @Pattern(regexp = "^[A-Z]{4}\\d{2}$|^[A-Z]{5}\\d{1}$", message = "Formato de patente inválido")
    private String plate;

    @NotBlank(message = "{NotBlank.vehicle.model}")
    @Size(min = 1, max = 25)
    private String model;

    private VehicleStatus status;

    public CreateVehicleRequest() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    
}
