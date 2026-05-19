package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;

import com.david.projects.springboot.delivery_routes.dto.request.CreateOrderRequest;
import com.david.projects.springboot.delivery_routes.dto.response.OrderResponse;

public interface OrderService {

    List<OrderResponse> findAll();

    Optional<OrderResponse> findById(Long id);

    OrderResponse save(CreateOrderRequest createOrderRequest);

    Optional<OrderResponse> update(Long id, CreateOrderRequest createOrderRequest);

    Optional<OrderResponse> delete(Long id);
}
