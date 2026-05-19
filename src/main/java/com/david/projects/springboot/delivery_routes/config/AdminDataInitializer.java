package com.david.projects.springboot.delivery_routes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.david.projects.springboot.delivery_routes.entities.Role;
import com.david.projects.springboot.delivery_routes.entities.User;
import com.david.projects.springboot.delivery_routes.repositories.RoleRepository;
import com.david.projects.springboot.delivery_routes.repositories.UserRepository;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataInitializer(UserRepository userRepository, 
                                RoleRepository roleRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("=== Inicializando datos de administrador ===");
        
        // Crear roles si no existen
        if (roleRepository.count() == 0) {
            System.out.println("Creando roles...");
            
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            
            System.out.println("Roles creados");
        } else {
            System.out.println("Los roles ya existen");
        }
        
        // Crear usuario admin si no existe
        if (!userRepository.findByUsername("admin").isPresent()) {
            System.out.println("Creando usuario admin...");
            
            // Buscar el rol ADMIN (debe existir después de crearlo o ya existía)
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    // Si no existe, crearlo
                    Role newRole = new Role();
                    newRole.setName("ROLE_ADMIN");
                    return roleRepository.save(newRole);
                });
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRole(adminRole);
            
            userRepository.save(admin);
            
            System.out.println("Usuario admin creado con éxito!");
            System.out.println("   Username: admin");
            System.out.println("   Password: admin123");
        } else {
            System.out.println("El usuario admin ya existe");
        }
        
        System.out.println("=== Inicialización completada ===");
    }
}