package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "special_earning_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialEarningType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specialEarningTypeId;

    private Long companyId;
    private Long branchId;

    private String name; // Bonus, Incentive, Arrears
    private Boolean taxable;
    private String description;
}
