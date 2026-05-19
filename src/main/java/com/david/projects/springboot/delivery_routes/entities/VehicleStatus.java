package com.david.projects.springboot.delivery_routes.entities;

public enum VehicleStatus {
    AVAILABLE,
    IN_ROUTE,
    MAINTENANCE;

    public String getSpanish(){
        switch (this) {
            case AVAILABLE:
                return "Disponible";
            case IN_ROUTE:
                return "En tránsito";
            case MAINTENANCE:
                return "Mantenimiento";
            default:
                return this.name();
        }
    }
}
