package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    public enum Role {
        SUPERADMIN,
        PREMIUMADMIN,
        COPMANYADMIN,
        BRANCADMIN,
        MANAGER,
        ADMINL1,
        ADMINL2,
        ADMINL3
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== AUTH =====
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Store roles as comma-separated values
    @Column(nullable = false)
    private String roles;

    // ===== HR LINK =====
    private Long employeeId;

    // ===== MULTI TENANCY =====
    @Column(nullable = true)
    private Long companyId;   // NULL for SUPERADMIN

    @Column(nullable = true)
    private Long branchId;    // NULL for company-level admins

    // ===== STATUS =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // ===== Helpers =====

    // Convert roles string -> Set<Role>
    public Set<Role> getRoleSet() {
        if (roles == null || roles.isEmpty()) return Set.of();
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    // Convert Set<Role> -> roles string
    public void setRoleSet(Set<Role> roleSet) {
        this.roles = roleSet.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }
}
