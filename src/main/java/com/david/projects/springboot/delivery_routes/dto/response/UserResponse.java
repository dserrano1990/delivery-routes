package com.david.projects.springboot.delivery_routes.dto.response;

public class UserResponse {

    private Long id;
    private String username;
    private boolean enabled;
    private boolean admin;
    private RoleResponse role;
    
    public UserResponse() {
    }

    public UserResponse(Long id, String username, boolean enabled, boolean admin, RoleResponse role) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.admin = admin;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public RoleResponse getRole() {
        return role;
    }

    public void setRole(RoleResponse role) {
        this.role = role;
    }

}
