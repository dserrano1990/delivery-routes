package com.david.projects.springboot.delivery_routes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.david.projects.springboot.delivery_routes.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
