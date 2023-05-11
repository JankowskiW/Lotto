package pl.wj.lotto.domain.common.drawdatetime;

import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.infrastructure.clock.AdjustableClock;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.gametype.GameTypeFakeConfig;
import pl.wj.lotto.infrastructure.gametype.properties.interval.GameTypeIntervalProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.GameTypeSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.LottoSettingsProperties;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DrawDateTimeCheckerTest {


    @Test
    void shouldReturnTheSameDayWhenThereIsStillAnyDraw() {
        // given
        Clock clock = new ClockFakeConfig().clock();
        GameTypeSettingsContainer gameTypeSettingsContainer = new GameTypeFakeConfig().gameTypeSettingsContainer();
        DrawDateTimeCheckerPort drawDateTimeCheckerPort = new DrawDateTimeChecker(clock, gameTypeSettingsContainer);

        GameType gameType = GameType.LOTTO;
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime nextDrawTime = LocalTime.of(22,0);
        LocalDateTime expectedResult = LocalDateTime.of(currentDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnTheSameTimeNextWeekWhenThereIsJustOneDrawPerWeek() {
        // given
        LocalDate date = LocalDate.of(2023, 5, 2);
        LocalTime time = LocalTime.of(22,0,0);
        Clock clock = AdjustableClock.ofLocalDateAndLocalTime(date, time, ZoneId.systemDefault());
        GameType gameType = GameType.LOTTO;

        GameTypeIntervalProperties gameTypeIntervalProperties = GameTypeIntervalProperties.builder()
                .lotto("0 0 22 * * 2")
                .build();

        GameTypeSettingsProperties lottoSettingsProperties = new LottoSettingsProperties();
        lottoSettingsProperties.setMainNumbersAmount(6);
        lottoSettingsProperties.setMainNumbersMinValue(1);
        lottoSettingsProperties.setMainNumbersMaxValue(49);
        Map<GameType, GameTypeSettingsProperties> settings = new HashMap<>();
        settings.put(gameType, lottoSettingsProperties);

        Map<GameType, String> intervals = new HashMap<>();
        intervals.put(gameType, gameTypeIntervalProperties.lotto());

        GameTypeSettingsContainer gameTypeSettingsContainer = GameTypeSettingsContainer.builder()
                .intervals(intervals)
                .settings(settings)
                .build();

        DrawDateTimeCheckerPort drawDateTimeCheckerPort = new DrawDateTimeChecker(clock, gameTypeSettingsContainer);

        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime nextDrawTime = currentDateTime.toLocalTime();
        LocalDate nextDrawDate = currentDate.plusDays(7);
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnFirstDrawInNextDayWhenThereIsNoMoreDrawsInCurrentDay() {
        // given
        LocalDate date = LocalDate.of(2023, 5, 2);
        LocalTime time = LocalTime.of(22,0,0);
        Clock clock = AdjustableClock.ofLocalDateAndLocalTime(date, time, ZoneId.systemDefault());
        GameType gameType = GameType.LOTTO;

        GameTypeIntervalProperties gameTypeIntervalProperties = GameTypeIntervalProperties.builder()
                .lotto("0 0 10,22 * * 2,3")
                .build();

        GameTypeSettingsProperties lottoSettingsProperties = new LottoSettingsProperties();
        lottoSettingsProperties.setMainNumbersAmount(6);
        lottoSettingsProperties.setMainNumbersMinValue(1);
        lottoSettingsProperties.setMainNumbersMaxValue(49);
        Map<GameType, GameTypeSettingsProperties> settings = new HashMap<>();
        settings.put(gameType, lottoSettingsProperties);

        Map<GameType, String> intervals = new HashMap<>();
        intervals.put(gameType, gameTypeIntervalProperties.lotto());

        GameTypeSettingsContainer gameTypeSettingsContainer = GameTypeSettingsContainer.builder()
                .intervals(intervals)
                .settings(settings)
                .build();

        DrawDateTimeCheckerPort drawDateTimeCheckerPort = new DrawDateTimeChecker(clock, gameTypeSettingsContainer);
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime nextDrawTime = LocalTime.of(10,0);
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
        LocalDate date = LocalDate.of(2023, 5, 2);
        LocalTime time = LocalTime.of(22,0,0);
        Clock clock = AdjustableClock.ofLocalDateAndLocalTime(date, time, ZoneId.systemDefault());
        GameType gameType = GameType.LOTTO;

        GameTypeIntervalProperties gameTypeIntervalProperties = GameTypeIntervalProperties.builder()
                .lotto("0 0 10,22 * * 1,2")
                .build();

        GameTypeSettingsProperties lottoSettingsProperties = new LottoSettingsProperties();
        lottoSettingsProperties.setMainNumbersAmount(6);
        lottoSettingsProperties.setMainNumbersMinValue(1);
        lottoSettingsProperties.setMainNumbersMaxValue(49);
        Map<GameType, GameTypeSettingsProperties> settings = new HashMap<>();
        settings.put(gameType, lottoSettingsProperties);

        Map<GameType, String> intervals = new HashMap<>();
        intervals.put(gameType, gameTypeIntervalProperties.lotto());

        GameTypeSettingsContainer gameTypeSettingsContainer = GameTypeSettingsContainer.builder()
                .intervals(intervals)
                .settings(settings)
                .build();

        DrawDateTimeCheckerPort drawDateTimeCheckerPort = new DrawDateTimeChecker(clock, gameTypeSettingsContainer);
        LocalTime nextDrawTime = LocalTime.of(10,0);
        LocalDate nextDrawDate = LocalDate.of(2023,5,8);
        LocalDateTime expectedResult = LocalDateTime.of(nextDrawDate, nextDrawTime);

        // when
        LocalDateTime result = drawDateTimeCheckerPort.getNextDrawDateTime(gameType);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }
}