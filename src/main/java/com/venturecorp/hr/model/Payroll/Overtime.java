package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "overtime")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Overtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long overtimeId;

    private Long companyId;
    private Long branchId;
    private Long branchIdRef; // optional reference
    private BigDecimal overtimeRate; // default
}
