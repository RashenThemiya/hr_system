package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "leave_policy",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"company_id", "branch_id", "name"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeavePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leavePolicyId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;     // 🔗 reference only (NO FK)

    @Column(name = "branch_id", nullable = false)
    private Long branchId;      // 🔗 reference only (NO FK)

    @Column(nullable = false)
    private String name;        // Annual, Sick, Casual

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
