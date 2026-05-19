package com.david.projects.springboot.delivery_routes.dto.response;

public class DeliveryPointResponse {

    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private Long orderId;
    
    public DeliveryPointResponse() {
    }

    public DeliveryPointResponse(Long id, String address, Double latitude, Double longitude, Long orderId) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    
}
