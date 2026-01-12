package com.venturecorp.hr.util;

import java.time.*;

public class TimeUtil {

    // Branch time → UTC
    public static OffsetDateTime branchTimeToUtc(
            String time,
            String branchTimezone
    ) {
        LocalTime localTime = LocalTime.parse(time);

        ZonedDateTime branchZoned = localTime
                .atDate(LocalDate.now())
                .atZone(ZoneId.of(branchTimezone));

        return branchZoned
                .withZoneSameInstant(ZoneOffset.UTC)
                .toOffsetDateTime();
    }

    // UTC → Branch time
    public static String utcToBranchTime(
            OffsetDateTime utcTime,
            String branchTimezone
    ) {
        return utcTime
                .atZoneSameInstant(ZoneId.of(branchTimezone))
                .toLocalTime()
                .toString();
    }
}
