package com.venturecorp.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;

    // Only include IDs or minimal info of related entities
    private Long companyId;
    private String companyName;

    private Long branchId;
    private String branchName;

    private Long managerId;
    private String managerName;
}
