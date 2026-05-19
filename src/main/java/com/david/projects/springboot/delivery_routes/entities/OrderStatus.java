package com.david.projects.springboot.delivery_routes.entities;

public enum OrderStatus {
    PENDING,
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED;

    public String getSpanish() {
        switch (this) {
            case PENDING:
                return "Pendiente";
            case ASSIGNED:
                return "Asignado";
            case IN_PROGRESS:
                return "En progreso";
            case COMPLETED:
                return "Completado";
            default:
                return this.name();
        }
    }
}
