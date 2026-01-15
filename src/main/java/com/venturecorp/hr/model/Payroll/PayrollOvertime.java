package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_overtime")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollOvertime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long branchId;

    private BigDecimal amount;
    private BigDecimal hourlyRate;
    private BigDecimal overtimeHours;
}

