package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.david.projects.springboot.delivery_routes.dto.request.CreateVehicleRequest;
import com.david.projects.springboot.delivery_routes.dto.response.VehicleResponse;
import com.david.projects.springboot.delivery_routes.entities.Vehicle;
import com.david.projects.springboot.delivery_routes.repositories.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> findAll() {
        List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll();

        return vehicles.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleResponse> findById(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        return vehicle.map(this::convertToResponse);
    }
    
    @Override
    @Transactional
    public VehicleResponse save(CreateVehicleRequest createVehicleRequest) {
        if (vehicleRepository.existsByPlate(createVehicleRequest.getPlate())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Vehículo con patente '" + createVehicleRequest.getPlate() + "' ya existe"
            );
        }
        
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(createVehicleRequest.getPlate());
        vehicle.setModel(createVehicleRequest.getModel());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return convertToResponse(savedVehicle);
    }
    
    @Override
    @Transactional
    public Optional<VehicleResponse> update(Long id, CreateVehicleRequest createVehicleRequest) {
        Vehicle vehicle = vehicleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Vehicle ID " + id + " no existe"
        ));

        if (!vehicle.getPlate().equals(createVehicleRequest.getPlate()) &&
        vehicleRepository.existsByPlate(createVehicleRequest.getPlate())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Vehiculo patente '" + createVehicleRequest.getPlate() + "' ya existe"
            );
        }

        vehicle.setPlate(createVehicleRequest.getPlate());
        vehicle.setModel(createVehicleRequest.getModel());
        vehicle.setStatus(createVehicleRequest.getStatus());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return Optional.of(convertToResponse(updatedVehicle));
    }
    
    @Override
    @Transactional
    public Optional<VehicleResponse> delete(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Vehicle ID " + id + " no existe"
        ));

        VehicleResponse response = convertToResponse(vehicle);

        vehicleRepository.delete(vehicle);

        return Optional.of(response);
    }

    private VehicleResponse convertToResponse(Vehicle vehicle) {
        return new VehicleResponse(
            vehicle.getId(),
            vehicle.getPlate(),
            vehicle.getModel(),
            vehicle.getStatus()
        );
    }
    
}
