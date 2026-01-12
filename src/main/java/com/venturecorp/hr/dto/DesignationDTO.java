package com.venturecorp.hr.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDTO {

    private Long designationId;
    private String designationName;
    private String hierarchyLevel;

    private Long companyId;
    private String companyName;

    private Long branchId;
    private String branchName;
}
