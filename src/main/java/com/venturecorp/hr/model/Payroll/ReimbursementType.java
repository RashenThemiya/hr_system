package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "reimbursement_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReimbursementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reimbursementTypeId;

    private Long companyId;
    private Long branchId;

    private String name; // Travel, Medical, Phone
    private Boolean taxable;
    private BigDecimal maxLimit; // optional
}
