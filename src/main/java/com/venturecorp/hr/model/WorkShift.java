package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String startTime; // "HH:mm" format
    private String endTime;
    private Boolean isOvernight;
    private Integer graceMinutes;
    private Integer halfDayMinutes;
    private Integer fullDayMinutes;
}
