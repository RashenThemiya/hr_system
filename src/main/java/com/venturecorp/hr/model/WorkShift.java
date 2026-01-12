package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "work_shift")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;

    private String name;

    // STORED IN UTC ONLY
    @Column(nullable = false)
    private OffsetDateTime startTimeUtc;

    @Column(nullable = false)
    private OffsetDateTime endTimeUtc;

    private Boolean isOvernight;
    private Integer graceMinutes;
    private Integer halfDayMinutes;
    private Integer fullDayMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyProfile company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}
