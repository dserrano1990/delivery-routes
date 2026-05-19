package com.david.projects.springboot.delivery_routes.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class CreateRoleRequest {

    @NotBlank
    private String name;

    private List<CreateUserRequest> users;

    public CreateRoleRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CreateUserRequest> getUsers() {
        return users;
    }

    public void setUsers(List<CreateUserRequest> users) {
        this.users = users;
    }
}
