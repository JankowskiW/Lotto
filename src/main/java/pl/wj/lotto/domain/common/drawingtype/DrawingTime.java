package pl.wj.lotto.domain.common.drawingtype;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Builder
public record DrawingTime (
    // timeInterval 0 means just once
    int timeInterval,
    TimeUnit timeIntervalUnit,
    // If fromTime equals toTime then drawing is exactly at that time
    LocalTime fromTime,
    LocalTime toTime,
    List<DayOfWeek> daysOfWeek
) {}
