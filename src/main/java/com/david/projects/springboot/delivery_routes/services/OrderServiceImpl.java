package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.david.projects.springboot.delivery_routes.dto.request.CreateOrderRequest;
import com.david.projects.springboot.delivery_routes.dto.response.DeliveryPointResponse;
import com.david.projects.springboot.delivery_routes.dto.response.OrderResponse;
import com.david.projects.springboot.delivery_routes.dto.response.VehicleResponse;
import com.david.projects.springboot.delivery_routes.entities.DeliveryPoint;
import com.david.projects.springboot.delivery_routes.entities.Order;
import com.david.projects.springboot.delivery_routes.entities.Vehicle;
import com.david.projects.springboot.delivery_routes.repositories.OrderRepository;
import com.david.projects.springboot.delivery_routes.repositories.VehicleRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final VehicleRepository vehicleRepository;

    public OrderServiceImpl(OrderRepository orderRepository, VehicleRepository vehicleRepository) {
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> orders = (List<Order>) orderRepository.findAll();

        return orders.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderResponse> findById(Long id) {
        return orderRepository.findById(id)
        .map(this::convertToResponse);
    }
    
    @Override
    @Transactional
    public OrderResponse save(CreateOrderRequest createOrderRequest) {
        Vehicle vehicleDB = vehicleRepository.findById(createOrderRequest.getVehicleId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Vehicle ID " + createOrderRequest.getVehicleId() + " no existe"
        ));

        Order order = new Order();
        order.setVehicle(vehicleDB);
            
        List<DeliveryPoint> deliveryPoints = createOrderRequest.getDeliveryPoints().stream()
        .map(deliveryPoint -> new DeliveryPoint(
            deliveryPoint.getAddress(),
            deliveryPoint.getLatitude(),
            deliveryPoint.getLongitude(),
            order
        ))
        .collect(Collectors.toList());

        order.setDeliveryPoints(deliveryPoints);

        Order saveOrder = orderRepository.save(order);

        return convertToResponse(saveOrder);
        
    }
    
    @Override
    @Transactional
    public Optional<OrderResponse> update(Long id, CreateOrderRequest createOrderRequest) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Orden ID " + id + " no existe"
        ));

        if (createOrderRequest.getStatus() != null) {
            order.setStatus(createOrderRequest.getStatus());
        }

        Vehicle vehicleDB = vehicleRepository.findById(createOrderRequest.getVehicleId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Vehicle ID " + createOrderRequest.getVehicleId() + " no existe"
        ));

        order.setVehicle(vehicleDB);

        order.getDeliveryPoints().clear();  // Esto activa orphanRemoval

        createOrderRequest.getDeliveryPoints().stream()
        .map(deliveryPoint -> new DeliveryPoint(
            deliveryPoint.getAddress(),
            deliveryPoint.getLatitude(),
            deliveryPoint.getLongitude(),
            order
        ))
        .forEach(order.getDeliveryPoints()::add);

        Order saveOrder = orderRepository.save(order);

        return Optional.of(convertToResponse(saveOrder));
    }
    
    @Override
    @Transactional
    public Optional<OrderResponse> delete(Long id) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Orden ID " + id + " no existe"
        ));

        OrderResponse response = convertToResponse(order);

        orderRepository.delete(order);

        return Optional.of(response);
    }
    
    private OrderResponse convertToResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getStatus(),
            order.getCreatedAt(),
            
            order.getDeliveryPoints().stream()
            .map(dp -> new DeliveryPointResponse(
                dp.getId(),
                dp.getAddress(),
                dp.getLatitude(),
                dp.getLongitude(),
                order.getId()
            ))
            .collect(Collectors.toList()),
            
            order.getVehicle() != null
            ? new VehicleResponse(
                order.getVehicle().getId(),
                order.getVehicle().getPlate(),
                order.getVehicle().getModel(),
                order.getVehicle().getStatus()
            )
            : null
        );
    }
}
