package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "statutory_contribution_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatutoryContributionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statutoryTypeId;

    private Long companyId;
    private Long branchId;

    private String name; // EPF_EMP, EPF_EMPR, ETF, PAYE
    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    private Payer payer; // EMPLOYEE / EMPLOYER

    private Boolean deduction;
    private Boolean taxableImpact;

    public enum Payer {
        EMPLOYEE,
        EMPLOYER
    }
}
