package com.example.NIS.security;

import com.example.NIS.model.User;
import com.example.NIS.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {
        // تنظيف المستخدمين السابقين
        userRepository.deleteAll();

        System.out.println("=== Creating Users ===");

        // إنشاء مستخدم admin
        User admin = new User();
        admin.setUsername("admin");

        String rawAdminPassword = "admin123";
        String encodedAdminPassword = passwordEncoder.encode(rawAdminPassword);
        System.out.println("Admin - Raw: " + rawAdminPassword);
        System.out.println("Admin - Encoded: " + encodedAdminPassword);

        admin.setPassword(encodedAdminPassword);
        admin.setRole("ADMIN");

        // إنشاء مستخدم عادي
        User user = new User();
        user.setUsername("user");

        String rawUserPassword = "user123";
        String encodedUserPassword = passwordEncoder.encode(rawUserPassword);
        System.out.println("User - Raw: " + rawUserPassword);
        System.out.println("User - Encoded: " + encodedUserPassword);

        user.setPassword(encodedUserPassword);
        user.setRole("USER");

        // حفظ في الداتابيز
        userRepository.saveAll(List.of(admin, user));

        System.out.println("=== Users Created Successfully ===");
    }
}