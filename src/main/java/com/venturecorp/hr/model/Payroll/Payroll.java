package com.venturecorp.hr.model.Payroll;
import com.venturecorp.hr.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    private Long companyId;
    private Long branchId;
    private String customPayrollId;

    @Column(nullable = false)
    private LocalDate periodStart;

    @Column(nullable = false)
    private LocalDate periodEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private String month; // YYYY-MM

    private BigDecimal grossSalary;
    private BigDecimal baseSalary;
    private BigDecimal taxableSalary;
    private BigDecimal tax;
    private Integer noPayDates;
    private Integer delayMinutes;
    private Integer earlyLeaveMinutes;
    private Integer overtimeMinutes;
    private BigDecimal noPayAmount;
    private BigDecimal delayAmount;
    private BigDecimal earlyLeaveAmount;
    private BigDecimal overtimeAmount;
    private BigDecimal netSalary;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CALCULATED,
        APPROVED,
        LOCKED
    }
}
