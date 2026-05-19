package com.david.projects.springboot.delivery_routes.repositories;

import org.springframework.data.repository.CrudRepository;

import com.david.projects.springboot.delivery_routes.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
