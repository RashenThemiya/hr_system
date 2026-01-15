package com.venturecorp.hr.model.Payroll;
import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deduction_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deductionTypeId;

    private Long companyId;
    private Long branchId;

    private String name; // Loan, Advance, Welfare
    private Boolean taxableImpact;
    private Boolean recurring;
    private String description;
    private Boolean statutoryContribution;
}
