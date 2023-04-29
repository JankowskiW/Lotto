package pl.wj.lotto.domain.common.drawtime.model;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Builder
public record DrawTime(
    // timeInterval 0 means just once
    int timeInterval,
    TimeUnit timeIntervalUnit,
    // If fromTime equals toTime then draw is exactly at that time (only if timeInterval is equal to 0)
    LocalTime fromTime,
    LocalTime toTime,
    List<DayOfWeek> daysOfWeek
) {
    public static DrawTimeBuilder builder() {
        return new ValidationBuilder();
    }

    private static class ValidationBuilder extends DrawTimeBuilder {
        @Override
        public DrawTime build() {
            if (super.timeInterval < 0)
                throw new RuntimeException("Attribute timeInterval must be greater than or equals 0");
            if (super.timeIntervalUnit == null)
                throw new RuntimeException("Attribute timeIntervalUnit must not be null");
            if (super.timeIntervalUnit != TimeUnit.MINUTES &&
                super.timeIntervalUnit != TimeUnit.HOURS &&
                super.timeIntervalUnit != TimeUnit.DAYS)
                throw new RuntimeException("Attribute timeIntervalUnit must be MINUTES or HOURS or DAYS (if once per day)");
            if (super.fromTime == null || super.toTime == null)
                throw new RuntimeException("Attributes fromTime and toTime must not be null");
            if (super.fromTime.isAfter(super.toTime))
                throw new RuntimeException("Attribute fromTime must be lower than or equals toTime");
            if (super.daysOfWeek == null || super.daysOfWeek.size() == 0)
                throw new RuntimeException("Days of week list must not be null or empty");
            Comparator<DayOfWeek> comparatorByDayIndex = Comparator.comparing(DayOfWeek::getValue);
            if (!isSorted(super.daysOfWeek, comparatorByDayIndex))
                throw new RuntimeException("Days of week list must have ascending order");
            return super.build();
        }

        private boolean isSorted(List<DayOfWeek> daysOfWeek, Comparator<DayOfWeek> dayOfWeekComparator) {
            if (daysOfWeek.isEmpty() || daysOfWeek.size() == 1) return true;
            Iterator<DayOfWeek> dayOfWeekIterator = daysOfWeek.iterator();
            DayOfWeek previous = dayOfWeekIterator.next();
            DayOfWeek current;
            while (dayOfWeekIterator.hasNext()) {
                current = dayOfWeekIterator.next();
                if (dayOfWeekComparator.compare(previous, current) > 0) {
                    return false;
                }
                previous = current;
            }
            return true;
        }
    }
}
