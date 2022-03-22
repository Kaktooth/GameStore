package com.store.gamestore.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateConverter {
    public static LocalDateTime localDateFromTimestamp(Timestamp timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()),
                TimeZone.getDefault().toZoneId());
    }
}
