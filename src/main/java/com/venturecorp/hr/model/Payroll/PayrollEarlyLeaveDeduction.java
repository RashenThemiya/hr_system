package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_early_leave_deduction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollEarlyLeaveDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    private Long employeeId;
    private Integer totalMinutes;
    private BigDecimal amount;
}
