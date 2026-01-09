package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_leave_policy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLeavePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_policy_id")
    private LeavePolicy leavePolicy;

    private LocalDate effectiveFrom;
    private LocalDate expireDate;
    private Integer priority; // higher priority overrides
}
