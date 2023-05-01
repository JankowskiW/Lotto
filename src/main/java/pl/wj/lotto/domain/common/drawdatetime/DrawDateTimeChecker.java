package pl.wj.lotto.domain.common.drawdatetime;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;

import java.time.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DrawDateTimeChecker implements DrawDateTimeCheckerPort {
    private final Clock clock;

    @Override
    public LocalDateTime getNextDrawDateTime(DrawDateTimeSettings drawDateTimeSettings) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate nextDrawDate = findNextDrawDate(drawDateTimeSettings, now);
        LocalTime nextDrawTime = findNextDrawTime(drawDateTimeSettings, now, nextDrawDate);
        return LocalDateTime.of(nextDrawDate, nextDrawTime);
    }

    @Override
    public LocalDateTime getNextDrawDateTimeForTicket(
            DrawDateTimeSettings drawDateTimeSettings, LocalDateTime generationDateTime) {
        LocalDate nextDrawDate = findNextDrawDate(drawDateTimeSettings, generationDateTime);
        LocalTime nextDrawTime = findNextDrawTime(drawDateTimeSettings, generationDateTime, nextDrawDate);
        return LocalDateTime.of(nextDrawDate, nextDrawTime);
    }

    private LocalDate findNextDrawDate(DrawDateTimeSettings drawDateTimeSettings, LocalDateTime now) {
        LocalTime nowTime = now.toLocalTime();
        LocalDate nowDate = now.toLocalDate();
        DayOfWeek nowDay = now.getDayOfWeek();
        if (drawDateTimeSettings.daysOfWeek().contains(nowDay) && nowTime.isBefore(drawDateTimeSettings.toTime()))
            return nowDate;
        DayOfWeek nextDrawDay = drawDateTimeSettings.daysOfWeek()
                .stream()
                .filter(d -> d.compareTo(nowDay) > 0)
                .findFirst()
                .orElse(drawDateTimeSettings.daysOfWeek().get(0));
        long daysToAdd = (7 + nextDrawDay.compareTo(nowDay)) % 7;
        daysToAdd = daysToAdd == 0 ? 7 : daysToAdd;
        return nowDate.plusDays(daysToAdd);
    }

    private LocalTime findNextDrawTime(DrawDateTimeSettings drawDateTimeSettings, LocalDateTime now, LocalDate nextDrawDate) {
        LocalTime nextDrawTime = drawDateTimeSettings.fromTime();
        if (drawDateTimeSettings.timeInterval() > 0 && now.toLocalDate().equals(nextDrawDate)) {
            while(!nextDrawTime.isAfter(now.toLocalTime()))
                nextDrawTime = increaseByInterval(nextDrawTime, drawDateTimeSettings.timeInterval(), drawDateTimeSettings.timeIntervalUnit());
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
