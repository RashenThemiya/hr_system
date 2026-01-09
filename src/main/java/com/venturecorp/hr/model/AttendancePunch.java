package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_punch")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendancePunch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long punchId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime punchTime;

    @Enumerated(EnumType.STRING)
    private PunchType punchType;

    @Enumerated(EnumType.STRING)
    private Source source;

    public enum PunchType {
        IN, OUT
    }

    public enum Source {
        WEB, QR, FINGERPRINT
    }
}
