package com.david.projects.springboot.delivery_routes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.david.projects.springboot.delivery_routes.dto.request.CreateUserRequest;
import com.david.projects.springboot.delivery_routes.dto.response.RoleResponse;
import com.david.projects.springboot.delivery_routes.dto.response.UserResponse;
import com.david.projects.springboot.delivery_routes.entities.Role;
import com.david.projects.springboot.delivery_routes.entities.User;
import com.david.projects.springboot.delivery_routes.repositories.RoleRepository;
import com.david.projects.springboot.delivery_routes.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        
        return users.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(Long id) {
        return userRepository.findById(id)
        .map(this::convertToResponse);
    }

    @Override
    @Transactional
    public UserResponse save(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,  // 409 Conflict
                "El username '" + createUserRequest.getUsername() + "' ya existe"
            );
        }

        String roleName = createUserRequest.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        Role role = roleRepository.findByName(roleName)
        .orElseThrow(() -> new RuntimeException("Role '" + roleName + "' no encontrado"));

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setRole(role);
        
        User saveUser = userRepository.save(user);

        return convertToResponse(saveUser);
    }
    
    @Override
    @Transactional
    public Optional<UserResponse> update(Long id, CreateUserRequest createUserRequest) {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "user ID " + id + " no existe"
        ));

        String roleName = createUserRequest.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        Role role = roleRepository.findByName(roleName)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Rol '" + roleName + "'' no existe"
        ));

        user.setUsername(createUserRequest.getUsername());
        if (createUserRequest.getPassword() != null && !createUserRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        }

        Boolean userEnabled = createUserRequest.isEnabled() != null
        ? createUserRequest.isEnabled() 
        : user.isEnabled();

        user.setEnabled(userEnabled);
        user.setRole(role);

        User updatedUser = userRepository.save(user); // ← Guardar cambios
        return Optional.of(convertToResponse(updatedUser));
    }
    
    @Override
    @Transactional
    public Optional<UserResponse> delete(Long id) {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "user ID " + id + " no existe"
        ));
        
        UserResponse response = convertToResponse(user);
        userRepository.delete(user);
        
        return Optional.of(response);
    }
    
    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEnabled(user.isEnabled());
        response.setAdmin(user.isAdmin());
        if (user.getRole() != null) {
            response.setRole(new RoleResponse(user.getRole().getName()));
        }
        return response;
    }
    
}
