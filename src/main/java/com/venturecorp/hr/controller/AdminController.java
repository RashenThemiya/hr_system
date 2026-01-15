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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ================= CREATE ADMIN =================
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
    public Admin createAdmin(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Set<String> roles,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long branchId
    ) {

        // Convert String roles -> Enum
        Set<Admin.Role> roleEnums = roles.stream()
                .map(String::toUpperCase)
                .map(Admin.Role::valueOf)
                .collect(Collectors.toSet());

        // 🔐 Validate company/branch based on role
        validateCompanyBranch(roleEnums, companyId, branchId);

        Admin admin = Admin.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .status(Admin.Status.ACTIVE)
                .companyId(companyId)
                .branchId(branchId)
                .build();

        admin.setRoleSet(roleEnums);

        return adminRepository.save(admin);
    }

    // ================= GET ALL ADMINS =================
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // ================= VALIDATION =================
    private void validateCompanyBranch(Set<Admin.Role> roles,
                                       Long companyId,
                                       Long branchId) {

        if (roles.contains(Admin.Role.SUPERADMIN)
                || roles.contains(Admin.Role.PREMIUMADMIN)) {

            if (companyId != null || branchId != null) {
                throw new IllegalArgumentException(
                        "SUPERADMIN / PREMIUMADMIN cannot have companyId or branchId");
            }
            return;
        }

        if (roles.contains(Admin.Role.COPMANYADMIN)) {
            if (companyId == null) {
                throw new IllegalArgumentException(
                        "COMPANYADMIN requires companyId");
            }
            return;
        }

        // Branch-level roles
        if (companyId == null || branchId == null) {
            throw new IllegalArgumentException(
                    "Branch-level admins require both companyId and branchId");
        }
    }
}
