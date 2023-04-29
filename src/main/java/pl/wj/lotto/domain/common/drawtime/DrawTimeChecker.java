package pl.wj.lotto.domain.common.drawtime;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawtime.model.DrawTime;

import java.time.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DrawTimeChecker {
    private final Clock clock;
    public LocalDateTime getNextDrawTime(DrawTime drawTime) {
        LocalDateTime nextDraw;
        LocalDateTime now = LocalDateTime.now(clock);
        LocalTime nowTime = now.toLocalTime();
        LocalDate nowDate = now.toLocalDate();
        if (drawTime.timeInterval() > 0) {
            int daysOffset = findOffsetBetweenNextDrawDayAndToday(drawTime, now);
            LocalDate nextDrawDate = nowDate.plusDays(7 - daysOffset);
            LocalTime nextDrawTime = findNextDrawTime(daysOffset, drawTime, nowTime);
            nextDraw = LocalDateTime.of(nextDrawDate, nextDrawTime);
        } else {
            int daysDiff = findOffsetBetweenNextDrawDayAndToday(drawTime, now);
            LocalDate nextDrawDate = nowDate.plusDays(7 - daysDiff);
            nextDraw = LocalDateTime.of(nextDrawDate, drawTime.toTime());
        }
        return nextDraw;
    }

    private LocalTime findNextDrawTime(int daysOffset, DrawTime drawTime, LocalTime nowTime) {
        LocalTime nextDrawTime = drawTime.fromTime(); // If next draw date is on another day, then next draw time equals fromTime
        if (Long.signum(daysOffset) == 0) {
            // If next draw date is on the same day, then next draw time has to be calculated
            while(!nextDrawTime.isAfter(drawTime.toTime())) {
                if (nextDrawTime.isAfter(nowTime)) break;
                nextDrawTime = increaseInterval(nextDrawTime, drawTime.timeInterval(), drawTime.timeIntervalUnit());
            }
        }
        return nextDrawTime;
    }

    private int findOffsetBetweenNextDrawDayAndToday(DrawTime drawTime, LocalDateTime now) {
        LocalTime nowTime = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond());
        DayOfWeek nowDay = now.getDayOfWeek();
        DayOfWeek nextDrawDay = drawTime.daysOfWeek()
                .stream()
                .filter(d ->
                        (d.compareTo(nowDay) == 0 && nowTime.isBefore(drawTime.toTime())) ||
                                (d.compareTo(nowDay) > 0))
                .findFirst().orElse(drawTime.daysOfWeek().get(0));
        return nextDrawDay.compareTo(nowDay);
    }

    private LocalTime increaseInterval(LocalTime time, int interval, TimeUnit timeUnit) {
        return switch(timeUnit) {
            case MINUTES -> time.plusMinutes(interval);
            case HOURS -> time.plusHours(interval);
            default -> throw new RuntimeException("Parameter timeUnit must be MINUTES or HOURS");
        };
    }
}
