package com.venturecorp.hr.dto;

import com.venturecorp.hr.model.LeavePolicy.LeaveType;
import lombok.Data;

@Data
public class LeavePolicyDTO {

    private Long leavePolicyId;

    private Long companyId;
    private Long branchId;

    private String name;
    private LeaveType leaveType;

    private Integer maxPerMonth;
    private Integer maxPerYear;

    private Boolean medicalRequired;
    private Boolean allowHalfDay;
    private Boolean allowShortLeave;
    private Boolean carryForward;
}
