package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;

import com.david.projects.springboot.delivery_routes.dto.request.CreateDeliveryPointRequest;
import com.david.projects.springboot.delivery_routes.dto.response.DeliveryPointResponse;

public interface DeliveryPointService {

    List<DeliveryPointResponse> findAll();

    Optional<DeliveryPointResponse> findById(Long id);

    DeliveryPointResponse save(CreateDeliveryPointRequest createDeliveryPointRequest);

    Optional<DeliveryPointResponse> update(Long id, CreateDeliveryPointRequest createDeliveryPointRequest);

    Optional<DeliveryPointResponse> delete(Long id);
}
