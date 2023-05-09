package pl.wj.lotto.domain.common.drawdatetime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.gametype.config.GameTypeSettingsConfig;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DrawDateTimeCheckerTest {
    private Clock clock;
    private DrawDateTimeCheckerPort drawDateTimeCheckerPort;

    @BeforeEach
    void setUp() {
        clock = new ClockFakeConfig().clock();
        GameTypeSettingsContainer gameTypeSettingsContainer = new GameTypeSettingsConfig().gameTypeSettingsContainer();
        drawDateTimeCheckerPort = new DrawDateTimeChecker(clock);
    }


    @Test
    void shouldReturnTheSameDayWhenThereIsStillAnyDraw() {
        // given
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime nextDrawTime = currentTime.plusHours(1);
        DrawDateTimeSettings drawDateTimeSettings = DrawDateTimeSettings.builder()
                .timeInterval(1)
                .timeIntervalUnit(TimeUnit.HOURS)
                .fromTime(currentTime.minusHours(1))
                .toTime(currentTime.plusHours(1))
                .daysOfWeek(daysOfWeek)
                .build();
        LocalDateTime expectedResult = LocalDateTime.of(currentDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(drawDateTimeSettings);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnTheSameDayNextWeekButEarlierIfThereIsDrawMoreThanOnceButJustInOneDayPerWeekAndThereIsNoMoreDrawsInCurrentDay() {
        // given
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(7);
        DrawDateTimeSettings drawDateTimeSettings = DrawDateTimeSettings.builder()
                .timeInterval(1)
                .timeIntervalUnit(TimeUnit.HOURS)
                .fromTime(currentTime.minusHours(1))
                .toTime(currentTime)
                .daysOfWeek(List.of(dayOfWeek))
                .build();
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(drawDateTimeSettings);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnTheSameTimeNextWeekWhenThereIsJustOneDrawPerWeek() {
        // given
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(7);
        DrawDateTimeSettings drawDateTimeSettings = DrawDateTimeSettings.builder()
                .timeInterval(0)
                .timeIntervalUnit(TimeUnit.DAYS)
                .fromTime(currentTime.minusHours(1))
                .toTime(currentTime.minusHours(1))
                .daysOfWeek(List.of(dayOfWeek))
                .build();
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(drawDateTimeSettings);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnFirstDrawInNextDayOfWeekWhenThereIsNoMoreDrawsInCurrentDay() {
        // given
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(1);
        DrawDateTimeSettings drawDateTimeSettings = DrawDateTimeSettings.builder()
                .timeInterval(10)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(currentTime.minusHours(1))
                .toTime(currentTime)
                .daysOfWeek(List.of(dayOfWeek, dayOfWeek.plus(1)))
                .build();
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(drawDateTimeSettings);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnFirstDrawInNextDrawDayWhenItIsInTheNextWeekIfThereIsNoMoreDrawsInCurrentDay() {
        // given
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(6);
        DrawDateTimeSettings drawDateTimeSettings = DrawDateTimeSettings.builder()
                .timeInterval(1)
                .timeIntervalUnit(TimeUnit.HOURS)
                .fromTime(currentTime.minusHours(1))
                .toTime(currentTime)
                .daysOfWeek(List.of(dayOfWeek.minus(1), dayOfWeek))
                .build();
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(drawDateTimeSettings);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenGivenTimeUnitIsNeitherMinutesNorHours() {
        // given
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        DrawDateTimeSettings drawDateTimeSettings = DrawDateTimeSettings.builder()
                .timeInterval(1)
                .timeIntervalUnit(TimeUnit.DAYS)
                .fromTime(currentDateTime.toLocalTime().minusHours(1))
                .toTime(currentDateTime.toLocalTime().plusHours(1))
                .daysOfWeek(List.of(DayOfWeek.THURSDAY))
                .build();

        // when && then
        assertThatThrownBy(() -> drawDateTimeCheckerPort.getNextDrawDateTime(drawDateTimeSettings))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Parameter timeUnit must be MINUTES or HOURS");
    }
}