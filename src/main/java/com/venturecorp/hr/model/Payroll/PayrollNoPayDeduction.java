package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_nopay_deduction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollNoPayDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long branchId;
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    private BigDecimal amount;
    private Integer daysAbsent;
    private BigDecimal baseSalary;
    private Integer monthDays;
}
