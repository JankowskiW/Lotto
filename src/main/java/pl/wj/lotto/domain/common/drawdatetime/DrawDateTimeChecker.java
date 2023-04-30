package pl.wj.lotto.domain.common.drawdatetime;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTime;

import java.time.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DrawDateTimeChecker {

    private final Clock clock;

    public LocalDateTime getNextDrawDateTime(DrawDateTime drawDateTime) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate nextDrawDate = findNextDrawDate(drawDateTime, now);
        LocalTime nextDrawTime = findNextDrawTime(drawDateTime, now, nextDrawDate);
        return LocalDateTime.of(nextDrawDate, nextDrawTime);
    }

    private LocalDate findNextDrawDate(DrawDateTime drawDateTime, LocalDateTime now) {
        LocalTime nowTime = now.toLocalTime();
        LocalDate nowDate = now.toLocalDate();
        DayOfWeek nowDay = now.getDayOfWeek();
        if (drawDateTime.daysOfWeek().contains(nowDay) && nowTime.isBefore(drawDateTime.toTime()))
            return nowDate;
        DayOfWeek nextDrawDay = drawDateTime.daysOfWeek()
                .stream()
                .filter(d -> d.compareTo(nowDay) > 0)
                .findFirst()
                .orElse(drawDateTime.daysOfWeek().get(0));
        long daysToAdd = (7 + nextDrawDay.compareTo(nowDay)) % 7;
        daysToAdd = daysToAdd == 0 ? 7 : daysToAdd;
        return nowDate.plusDays(daysToAdd);
    }

    private LocalTime findNextDrawTime(DrawDateTime drawDateTime, LocalDateTime now, LocalDate nextDrawDate) {
        LocalTime nextDrawTime = drawDateTime.fromTime();
        if (drawDateTime.timeInterval() > 0 && now.toLocalDate().equals(nextDrawDate)) {
            while(!nextDrawTime.isAfter(now.toLocalTime()))
                nextDrawTime = increaseByInterval(nextDrawTime, drawDateTime.timeInterval(), drawDateTime.timeIntervalUnit());
        }
        return nextDrawTime;
    }

    private LocalTime increaseByInterval(LocalTime time, int interval, TimeUnit timeUnit) {
        return switch(timeUnit) {
            case MINUTES -> time.plusMinutes(interval);
            case HOURS -> time.plusHours(interval);
            default -> throw new RuntimeException("Parameter timeUnit must be MINUTES or HOURS");
        };
    }
}
