package com.outbrain.rss.infrastructure.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateTimeConverter {

    public LocalDateTime convert(Date date){
        return date == null ? null : date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
