package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;   

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "special_earning_tax")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialEarningTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long branchId;

    private String name;
    private BigDecimal incomeMin;
    private BigDecimal incomeMax;
    private BigDecimal rate;
    private BigDecimal less;
    private Boolean deduction;
    private Boolean resident;
}
