package com.venturecorp.hr.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkShiftDTO {

    private Long shiftId;
    private String name;

    // FRONTEND TIME (Branch Timezone)
    private String startTime; // HH:mm
    private String endTime;   // HH:mm

    private Integer graceMinutes;
    private Integer halfDayMinutes;
    private Integer fullDayMinutes;

    private Long companyId;
    private Long branchId;
    private String timezone;
}
