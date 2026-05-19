package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.david.projects.springboot.delivery_routes.dto.request.CreateDeliveryPointRequest;
import com.david.projects.springboot.delivery_routes.dto.response.DeliveryPointResponse;
import com.david.projects.springboot.delivery_routes.entities.DeliveryPoint;
import com.david.projects.springboot.delivery_routes.repositories.DeliveryPointRepository;
import com.david.projects.springboot.delivery_routes.repositories.OrderRepository;

// @Service
public class DeliveryPointServiceImpl implements DeliveryPointService {

    DeliveryPointRepository deliveryPointRepository;

    OrderRepository orderRepository;

    public DeliveryPointServiceImpl(DeliveryPointRepository deliveryPointRepository, OrderRepository orderRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<DeliveryPointResponse> findAll() {
        List<DeliveryPoint> deliveryPoints = (List<DeliveryPoint>) deliveryPointRepository.findAll();

        return deliveryPoints.stream()
        .map(deliveryPoint -> {
            return new DeliveryPointResponse(
                deliveryPoint.getId(),
                deliveryPoint.getAddress(),
                deliveryPoint.getLatitude(),
                deliveryPoint.getLongitude(),
                deliveryPoint.getOrder().getId()
            );
        })
        .collect(Collectors.toList());
    }

    @Override
    public Optional<DeliveryPointResponse> findById(Long id) {
        Optional<DeliveryPoint> deliveryPoint = deliveryPointRepository.findById(id);

        return deliveryPoint.map(dp -> new DeliveryPointResponse(
            dp.getId(),
            dp.getAddress(),
            dp.getLatitude(),
            dp.getLongitude(),
            dp.getOrder().getId()
        ));
    }
    
    @Override
    public DeliveryPointResponse save(CreateDeliveryPointRequest createDeliveryPointRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<DeliveryPointResponse> update(Long id, CreateDeliveryPointRequest createDeliveryPointRequest) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    
    @Override
    public Optional<DeliveryPointResponse> delete(Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    
}
