package com.venturecorp.hr.dto;

import com.venturecorp.hr.model.Admin;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String token;

    private Long adminId;
    private Long employeeId;

    private Long companyId;
    private Long branchId;

    private Set<Admin.Role> roles;
    private Admin.Status status;
}
