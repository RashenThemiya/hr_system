package com.venturecorp.hr.model.Payroll;

import com.venturecorp.hr.model.*;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "early_leave")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarlyLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long branchId;

    private Integer graceMinutes;
    private Integer maxDailyMinutes;
    private BigDecimal ratePerMinute;
}
