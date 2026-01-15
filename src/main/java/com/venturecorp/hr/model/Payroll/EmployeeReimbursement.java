package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_reimbursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeReimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeReimbursementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reimbursement_type_id")
    private ReimbursementType reimbursementType;

    private BigDecimal claimAmount;
    private String claimMonth; // YYYY-MM

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
