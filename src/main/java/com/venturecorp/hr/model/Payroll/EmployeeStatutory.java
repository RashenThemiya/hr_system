package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_statutory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeStatutory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeStatutoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statutory_type_id")
    private StatutoryContributionType statutoryContributionType;

    private BigDecimal customRate; // nullable
}
