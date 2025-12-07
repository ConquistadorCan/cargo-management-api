package com.mfc.logistics.cargo_management_api.config;

import com.mfc.logistics.cargo_management_api.model.User;
import com.mfc.logistics.cargo_management_api.repository.UserRepository;
import com.mfc.logistics.cargo_management_api.service.implementation.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Optional;

@Configuration
public class ApplicationConfig {
    private final UserRepository userRepository;

    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetails() {
        return username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new UserDetailsImpl(user.get());
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetails());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().ignoreIfMissing().load();
    }
}
