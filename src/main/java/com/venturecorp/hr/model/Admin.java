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
        PREMIUMADMIN,
        SUPERADMIN,
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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Store roles as comma-separated string
    @Column(name = "roles")
    private String roles;

    private Long employeeId;

    // ✅ New status column
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // Helper: roles string -> Set<Role>
    public Set<Role> getRoleSet() {
        if (roles == null || roles.isEmpty()) return Set.of();
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    // Helper: Set<Role> -> roles string
    public void setRoleSet(Set<Role> roleSet) {
        this.roles = roleSet.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }
}
