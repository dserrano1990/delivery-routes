package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;

import com.david.projects.springboot.delivery_routes.dto.request.CreateVehicleRequest;
import com.david.projects.springboot.delivery_routes.dto.response.VehicleResponse;

public interface VehicleService {

    List<VehicleResponse> findAll();

    Optional<VehicleResponse> findById(Long id);

    VehicleResponse save(CreateVehicleRequest createVehicleRequest);

    Optional<VehicleResponse> update(Long id, CreateVehicleRequest createVehicleRequest);

    Optional<VehicleResponse> delete(Long id);
}
