package com.venturecorp.hr.controller;

import com.venturecorp.hr.dto.LoginRequest;
import com.venturecorp.hr.dto.LoginResponse;
import com.venturecorp.hr.model.Admin;
import com.venturecorp.hr.repository.AdminRepository;
import com.venturecorp.hr.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (admin.getStatus() != Admin.Status.ACTIVE) {
            throw new RuntimeException("Account is inactive");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(admin);

        return LoginResponse.builder()
                .token(token)
                .adminId(admin.getId())
                .employeeId(admin.getEmployeeId())
                .companyId(admin.getCompanyId())
                .branchId(admin.getBranchId())
                .roles(admin.getRoleSet())
                .status(admin.getStatus())
                .build();
    }
}
