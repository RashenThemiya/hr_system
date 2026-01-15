package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_wise_overtime")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeWiseOvertime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overtime_id")
    private Overtime overtime;

    private Long companyId;
    private Long branchId;
    private BigDecimal overtimeRate; // employee specific
}
