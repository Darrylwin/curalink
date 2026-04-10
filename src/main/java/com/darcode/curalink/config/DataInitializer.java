package com.darcode.curalink.config;

import com.darcode.curalink.enums.Role;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeAdminUser(UserRepository userRepository) {
        return args -> {
            String adminEmail = "admin@curalink.com";

            // Vérifier si l'admin existe déjà
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
                System.out.println("✓ Admin user créé avec succès : admin@curalink.com");
            } else {
                System.out.println("✓ Admin user existe déjà");
            }
        };
    }
}

