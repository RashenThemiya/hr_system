package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_policy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeavePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leavePolicyId;

    private Long companyId;
    private String name; // Annual, Sick, Casual

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private Integer maxPerMonth;
    private Integer maxPerYear;
    private Boolean medicalRequired;
    private Boolean allowHalfDay;
    private Boolean allowShortLeave;
    private Boolean carryForward;
    private LocalDateTime createdAt;

    public enum LeaveType {
        FULL, HALF, SHORT
    }
}
