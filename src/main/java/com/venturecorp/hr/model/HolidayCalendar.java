package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "holiday_calendar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidayId;

    private Long companyId;

    private LocalDate holidayDate;
    private String name;
    private Boolean isRecurring;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
