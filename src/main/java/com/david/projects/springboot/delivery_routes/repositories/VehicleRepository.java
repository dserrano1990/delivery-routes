package com.david.projects.springboot.delivery_routes.repositories;

import org.springframework.data.repository.CrudRepository;

import com.david.projects.springboot.delivery_routes.entities.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    boolean existsByPlate(String plate);
}
