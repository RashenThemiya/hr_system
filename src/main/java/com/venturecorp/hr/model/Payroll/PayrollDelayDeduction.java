package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_delay_deduction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollDelayDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    private Long companyId;
    private Long employeeId;
    private Integer totalMinutes;
    private BigDecimal totalAmount;
}
