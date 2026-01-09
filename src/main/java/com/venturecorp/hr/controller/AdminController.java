package com.venturecorp.hr.controller;

import com.venturecorp.hr.model.Admin;
import com.venturecorp.hr.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // Use BCrypt for password hashing
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   @PostMapping("/create")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public Admin createAdmin(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam Set<String> roles) {

    // Convert String roles -> Admin.Role
    Set<Admin.Role> roleEnums = roles.stream()
            .map(String::toUpperCase)
            .map(Admin.Role::valueOf)
            .collect(Collectors.toSet());

    Admin admin = Admin.builder()
            .username(username)
            .password(passwordEncoder.encode(password)) // Encrypt password
            .status(Admin.Status.ACTIVE)                 // ✅ Default ACTIVE
            .build();

    admin.setRoleSet(roleEnums);

    return adminRepository.save(admin);
}
    // Get all admins
    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
