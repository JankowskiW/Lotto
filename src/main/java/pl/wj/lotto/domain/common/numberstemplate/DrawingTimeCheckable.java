package pl.wj.lotto.domain.common.numberstemplate;

import pl.wj.lotto.domain.common.drawingtype.DrawingTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public interface DrawingTimeCheckable {
    LocalDateTime getNextDrawingTime();
    default LocalDateTime getNextDrawingTime(DrawingTime drawingTime) {
        LocalDateTime nextDrawing;
        LocalDateTime now = LocalDateTime.now();
        LocalTime nowTime = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond());
        if (drawingTime.timeInterval() > 0) {
            int daysOffset = findOffsetBetweenNextDrawingDayAndToday(drawingTime, now);
            LocalDate nextDrawingDate = LocalDate.now().plusDays(7 - daysOffset);
            LocalTime nextDrawingTime = findNextDrawingTime(daysOffset, drawingTime, nowTime);
            nextDrawing = LocalDateTime.of(nextDrawingDate, nextDrawingTime);
        } else {
            int daysDiff = findOffsetBetweenNextDrawingDayAndToday(drawingTime, now);
            LocalDate nextDrawingDate = LocalDate.now().plusDays(7 - daysDiff);
            nextDrawing = LocalDateTime.of(nextDrawingDate, drawingTime.toTime());
        }
        return nextDrawing;
    }

    private LocalTime findNextDrawingTime(int daysOffset, DrawingTime drawingTime, LocalTime nowTime) {
        LocalTime nextDrawingTime = drawingTime.fromTime(); // If next drawing date is on another day, then next drawing time equals fromTime
        if (Long.signum(daysOffset) == 0) {
            // If next drawing date is on the same day, then next drawing time has to be calculated
            while(!nextDrawingTime.isAfter(drawingTime.toTime())) {
                if (nextDrawingTime.isAfter(nowTime)) break;
                nextDrawingTime = increaseInterval(nextDrawingTime, drawingTime.timeInterval(), drawingTime.timeIntervalUnit());
            }
        }
        return nextDrawingTime;
    }

    private int findOffsetBetweenNextDrawingDayAndToday(DrawingTime drawingTime, LocalDateTime now) {
        LocalTime nowTime = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond());
        DayOfWeek nowDay = now.getDayOfWeek();
        DayOfWeek nextDrawingDay = drawingTime.daysOfWeek()
                .stream()
                .filter(d ->
                        (d.compareTo(nowDay) == 0 && nowTime.isBefore(drawingTime.toTime())) ||
                                (d.compareTo(nowDay) > 0))
                .findFirst().orElse(drawingTime.daysOfWeek().get(0));
        return nextDrawingDay.compareTo(nowDay);
    }

    private LocalTime increaseInterval(LocalTime time, int interval, TimeUnit timeUnit) {
        return switch(timeUnit) {
            case MINUTES -> time.plusMinutes(interval);
            case HOURS -> time.plusHours(interval);
            default -> throw new RuntimeException("Parameter timeUnit must be MINUTES or HOURS");
        };
    }
}
