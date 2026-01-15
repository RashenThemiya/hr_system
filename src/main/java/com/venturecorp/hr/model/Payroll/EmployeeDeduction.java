package com.venturecorp.hr.model.Payroll;
import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_deduction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeDeductionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deduction_type_id")
    private DeductionType deductionType;

    private BigDecimal amount;
    private String startMonth; // YYYY-MM
    private String endMonth; // YYYY-MM
    private Boolean active;
}

