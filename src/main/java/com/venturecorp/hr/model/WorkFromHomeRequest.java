package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_wfh_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFromHomeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which employee is applying
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 1000)
    private String reason;          // Why WFH

    @Enumerated(EnumType.STRING)
    private Status status;          // pending, manager_approved, hr_approved, approved, rejected

    private LocalDate appliedAt;    // date employee applied

    private String notes;           // optional notes from manager/HR

    public enum Status {
        PENDING,
        MANAGER_APPROVED,
        HR_APPROVED,
        APPROVED,
        REJECTED
    }
}
