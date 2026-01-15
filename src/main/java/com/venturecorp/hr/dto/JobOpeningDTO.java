package com.venturecorp.hr.dto;

import com.venturecorp.hr.model.JobOpening;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOpeningDTO {

    private Long id;

    private Long companyId;
    private Long branchId;

    private String jobTopic;
    private String postImageLink;
    private String description;
    private String experience;
    private String responsibility;
    private String qualification;
    private LocalDate closingDate;

    private JobOpening.Status status;
    private JobOpening.Level level;

    private String linkedin;
    private String topJob;
    private String link;
}
