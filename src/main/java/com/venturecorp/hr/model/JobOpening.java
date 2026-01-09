package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_opening")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOpening {

    public enum Status {
        OPEN, WAITING_APPROVAL, DRAFT, PUBLISHED, DENIED, COMPLETED
    }

    public enum Level {
        URGENT, REPLACEMENT, EXPANSION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTopic;
    private String postImageLink;
    private String description;
    private String experience;
    private String responsibility;
    private String qualification;
    private LocalDate closingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String linkedin; // YES/NO
    private String topJob; // YES/NO
    private String link;
}
