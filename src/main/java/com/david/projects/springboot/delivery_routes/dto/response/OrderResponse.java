package com.david.projects.springboot.delivery_routes.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.david.projects.springboot.delivery_routes.entities.OrderStatus;

public class OrderResponse {

    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<DeliveryPointResponse> deliveryPoints;
    private VehicleResponse vehicle;
    
    public OrderResponse() {
    }

    public OrderResponse(Long id, OrderStatus status, LocalDateTime createdAt, List<DeliveryPointResponse> deliveryPoints,
            VehicleResponse vehicle) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.deliveryPoints = deliveryPoints;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<DeliveryPointResponse> getDeliveryPoints() {
        return deliveryPoints;
    }

    public void setDeliveryPoints(List<DeliveryPointResponse> deliveryPoints) {
        this.deliveryPoints = deliveryPoints;
    }

    public VehicleResponse getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleResponse vehicle) {
        this.vehicle = vehicle;
    }

    

    
}
