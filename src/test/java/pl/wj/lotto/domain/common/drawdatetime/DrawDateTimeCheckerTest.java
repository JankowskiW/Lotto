package pl.wj.lotto.domain.common.drawdatetime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.gametype.GameTypeFakeConfig;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DrawDateTimeCheckerTest {
    private Clock clock;
    private DrawDateTimeCheckerPort drawDateTimeCheckerPort;

    @BeforeEach
    void setUp() {
        clock = new ClockFakeConfig().clock();
        GameTypeSettingsContainer gameTypeSettingsContainer = new GameTypeFakeConfig().gameTypeSettingsContainer();
        drawDateTimeCheckerPort = new DrawDateTimeChecker(clock, gameTypeSettingsContainer);
    }


    @Test
    void shouldReturnTheSameDayWhenThereIsStillAnyDraw() {
        // given
        GameType gameType = GameType.LOTTO;
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime nextDrawTime = currentTime.plusHours(1);
        LocalDateTime expectedResult = LocalDateTime.of(currentDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnTheSameDayNextWeekButEarlierIfThereIsDrawMoreThanOnceButJustInOneDayPerWeekAndThereIsNoMoreDrawsInCurrentDay() {
        // given
        GameType gameType = GameType.LOTTO;
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(7);
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnTheSameTimeNextWeekWhenThereIsJustOneDrawPerWeek() {
        // given
        GameType gameType = GameType.LOTTO;
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(7);
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnFirstDrawInNextDayOfWeekWhenThereIsNoMoreDrawsInCurrentDay() {
        // given
        GameType gameType = GameType.LOTTO;
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(1);
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnFirstDrawInNextDrawDayWhenItIsInTheNextWeekIfThereIsNoMoreDrawsInCurrentDay() {
        // given
        GameType gameType = GameType.LOTTO;
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime nextDrawTime = currentTime.minusHours(1);
        LocalDate nextDrawDate = currentDate.plusDays(6);
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenGivenTimeUnitIsNeitherMinutesNorHours() {
        // given
        GameType gameType = GameType.LOTTO;

        // when && then
        assertThatThrownBy(() -> drawDateTimeCheckerPort.getNextDrawDateTime(gameType))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Parameter timeUnit must be MINUTES or HOURS");
    }
}