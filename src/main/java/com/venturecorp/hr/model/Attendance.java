package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private WorkShift shift;

    private LocalDate attendanceDate;
    private LocalTime firstInTime;
    private LocalTime lastOutTime;

    private Integer workedMinutes;
    private Integer lateMinutes;
    private Integer earlyLeaveMinutes;
    private Integer overtimeMinutes;

    @Enumerated(EnumType.STRING)
    private Status status; // general status

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private Quarter leaveQuarter;

    @Enumerated(EnumType.STRING)
    private Location location;

    public enum Status {
        PRESENT, ABSENT, HOLIDAY, LEAVE
    }

    public enum LeaveType {
        FULL, HALF, SHORT
    }

    public enum Quarter {
        ONE, TWO, THREE, FOUR
    }

    public enum Location {
        OFFICE, WORKFROMHOME, LEAVE
    }
}
