package pl.wj.lotto.domain.common.drawtime;

import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawtime.model.DrawTime;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DrawTimeCheckerTest {
    private final Clock clock = new ClockFakeConfig().clock();
    @Test
    void shouldReturnNextDrawTimeInTheSameDayWhenThereIsMoreThanOneDrawOnThatDay() {
        // given
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        LocalDate currentDate = LocalDate.of(2023,1,1);
        LocalTime nextDrawTime = LocalTime.of(12, 10);
        DrawTime drawTime = DrawTime.builder()
                .timeInterval(10)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(LocalTime.of(6,0))
                .toTime(LocalTime.of(23,0))
                .daysOfWeek(daysOfWeek)
                .build();
        DrawTimeChecker drawTimeChecker = new DrawTimeChecker(clock);
        LocalDateTime expectedResult = LocalDateTime.of(currentDate, nextDrawTime);

        // when
        LocalDateTime result = drawTimeChecker.getNextDrawTime(drawTime);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }
}