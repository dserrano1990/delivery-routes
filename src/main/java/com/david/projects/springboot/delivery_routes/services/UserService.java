package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;

import com.david.projects.springboot.delivery_routes.dto.request.CreateUserRequest;
import com.david.projects.springboot.delivery_routes.dto.response.UserResponse;

public interface UserService {

    List<UserResponse> findAll();

    Optional<UserResponse> findById(Long id);

    UserResponse save(CreateUserRequest createUserRequest);

    Optional<UserResponse> update(Long id, CreateUserRequest createUserRequest);

    Optional<UserResponse> delete(Long id);

    // boolean existsByUsername(String username);
}
