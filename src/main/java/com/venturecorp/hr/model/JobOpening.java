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

    // 🔑 REQUIRED for filtering
    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Long branchId;

    private String jobTopic;
    private String postImageLink;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String experience;

    @Column(columnDefinition = "TEXT")
    private String responsibility;

    @Column(columnDefinition = "TEXT")
    private String qualification;

    private LocalDate closingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String linkedin; // YES / NO
    private String topJob;   // YES / NO
    private String link;
}
