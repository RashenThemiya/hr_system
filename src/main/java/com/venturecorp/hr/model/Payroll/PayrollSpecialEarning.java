package com.venturecorp.hr.model.Payroll;
import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_special_earning")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollSpecialEarning {

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
    @JoinColumn(name = "special_earning_type_id")
    private SpecialEarningType specialEarningType;

    private BigDecimal amount;
}
