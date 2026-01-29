package com.example.NIS.security;

import com.example.NIS.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Optional;

@Configuration
public class SimpleUserService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            // جرب تجيب المستخدم من الداتابيز أولاً
            Optional<com.example.NIS.model.User> dbUser = userRepository.findByUsername(username);

            if (dbUser.isPresent()) {
                com.example.NIS.model.User user = dbUser.get();
                return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();
            }

            // إذا مش موجود في الداتابيز، استخدم الـ in-memory
            return createInMemoryUser(username);
        };
    }

    private UserDetails createInMemoryUser(String username) {
        PasswordEncoder encoder = passwordEncoder();

        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(encoder.encode("admin123"))
                    .roles("ADMIN")
                    .build();
        } else if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password(encoder.encode("user123"))
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}