package com.venturecorp.hr.model.Payroll;
import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "earning_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarningType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long earningTypeId;

    private Long companyId;
    private Long branchId;

    private String name; // Basic, Travel, Allowance
    private Boolean base; // yes/no
    private Boolean noPay; // yes/no
    private Boolean taxable;
    private Boolean fixedAmount;
    private Boolean statutoryContribution;
    private Boolean overtime;
}
