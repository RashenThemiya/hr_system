package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_earning")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeEarningId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_type_id")
    private EarningType earningType;

    private BigDecimal defaultAmount;
    private Boolean active;
}
