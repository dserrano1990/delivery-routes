package com.david.projects.springboot.delivery_routes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.david.projects.springboot.delivery_routes.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
