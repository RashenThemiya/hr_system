package com.venturecorp.hr.config;

import com.venturecorp.hr.model.Admin;
import com.venturecorp.hr.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) {
            Admin defaultAdmin = Admin.builder()
                                      .username("superadmin")
                                      .password(passwordEncoder.encode("admin123"))
                                     .status(Admin.Status.ACTIVE)   // ✅ REQUIRED
                                      .build();

            defaultAdmin.setRoleSet(Set.of(Admin.Role.PREMIUMADMIN));

            adminRepository.save(defaultAdmin);

            System.out.println("Default admin created: superadmin / admin123");
        }
    }
}
