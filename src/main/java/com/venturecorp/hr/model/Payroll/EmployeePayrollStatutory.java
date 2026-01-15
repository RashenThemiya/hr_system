package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_payroll_statutory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollStatutory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statutory_type_id")
    private StatutoryContributionType statutoryContributionType;

    private BigDecimal rate;
    private BigDecimal baseAmount;
    private BigDecimal calculatedAmount;
}
