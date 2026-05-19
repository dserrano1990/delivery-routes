package com.david.projects.springboot.delivery_routes.dto.response;

import com.david.projects.springboot.delivery_routes.entities.VehicleStatus;

public class VehicleResponse {

    private Long id;
    private String plate;
    private String model;
    private VehicleStatus status;
    
    public VehicleResponse() {
    }

    public VehicleResponse(Long id, String plate, String model, VehicleStatus status) {
        this.id = id;
        this.plate = plate;
        this.model = model;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
