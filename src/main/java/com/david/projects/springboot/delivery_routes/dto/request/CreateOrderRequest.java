package com.david.projects.springboot.delivery_routes.dto.request;

import java.util.List;

import com.david.projects.springboot.delivery_routes.entities.OrderStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateOrderRequest {

    private OrderStatus status;

    @NotEmpty(message = "{NotEmpty.order.deliveryPoints}")
    private List<@Valid CreateDeliveryPointRequest> deliveryPoints;

    @NotNull(message = "{NotNull.order.vehicleId}")
    private Long vehicleId;

    public CreateOrderRequest() {
    }

    public List<CreateDeliveryPointRequest> getDeliveryPoints() {
        return deliveryPoints;
    }

    public void setDeliveryPoints(List<CreateDeliveryPointRequest> deliveryPoints) {
        this.deliveryPoints = deliveryPoints;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
}
