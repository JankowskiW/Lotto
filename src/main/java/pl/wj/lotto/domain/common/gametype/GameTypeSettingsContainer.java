package pl.wj.lotto.domain.common.gametype;

import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTime;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameTypeSettingsContainer {
    private static Map<GameType, GameTypeSettings> settings;

    static {
        populateRequirements();
    }

    public static GameTypeSettings getGameTypeSettings(GameType gameType) {
        return settings.get(gameType);
    }

    private static void populateRequirements() {
        settings = new HashMap<>();
        settings.put(GameType.LOTTO, createLottoSettings());
        settings.put(GameType.Q600, createQuick600Settings());
        settings.put(GameType.EJP, createEurojackpotSettings());
        settings.put(GameType.KENO, createKenoSettings());
    }

    private static GameTypeSettings createLottoSettings() {
        DrawDateTime drawDateTime = DrawDateTime.builder()
                .timeInterval(0)
                .timeIntervalUnit(TimeUnit.DAYS)
                .fromTime(LocalTime.of(22,0,0))
                .toTime(LocalTime.of(22,0,0))
                .daysOfWeek(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY))
                .build();
        return GameTypeSettings.builder()
                .gameType(GameType.LOTTO)
                .drawDateTime(drawDateTime)
                .minAmountOfMainNumbers(6)
                .maxAmountOfMainNumbers(6)
                .minValueOfMainNumbers(1)
                .maxValueOfMainNumbers(49)
                .minAmountOfExtraNumbers(0)
                .maxAmountOfExtraNumbers(0)
                .minValueOfExtraNumbers(0)
                .maxValueOfExtraNumbers(0)
                .build();
    }

    private static GameTypeSettings createQuick600Settings() {
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        DrawDateTime drawDateTime = DrawDateTime.builder()
                .timeInterval(4)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(LocalTime.of(6,0,0))
                .toTime(LocalTime.of(22,58,0))
                .daysOfWeek(daysOfWeek)
                .build();
        return GameTypeSettings.builder()
                .gameType(GameType.Q600)
                .drawDateTime(drawDateTime)
                .minAmountOfMainNumbers(6)
                .maxAmountOfMainNumbers(6)
                .minValueOfMainNumbers(1)
                .maxValueOfMainNumbers(32)
                .minAmountOfExtraNumbers(0)
                .maxAmountOfExtraNumbers(0)
                .minValueOfExtraNumbers(0)
                .maxValueOfExtraNumbers(0)
                .build();
    }

    private static GameTypeSettings createEurojackpotSettings() {
        DrawDateTime drawDateTime = DrawDateTime.builder()
                .timeInterval(0)
                .timeIntervalUnit(TimeUnit.DAYS)
                .fromTime(LocalTime.of(21,0,0))
                .toTime(LocalTime.of(21,0,0))
                .daysOfWeek(List.of(DayOfWeek.TUESDAY, DayOfWeek.SATURDAY))
                .build();
        return GameTypeSettings.builder()
                .gameType(GameType.EJP)
                .drawDateTime(drawDateTime)
                .minAmountOfMainNumbers(5)
                .maxAmountOfMainNumbers(5)
                .minValueOfMainNumbers(1)
                .maxValueOfMainNumbers(50)
                .minAmountOfExtraNumbers(2)
                .maxAmountOfExtraNumbers(2)
                .minValueOfExtraNumbers(1)
                .maxValueOfExtraNumbers(12)
                .build();
    }

    private static GameTypeSettings createKenoSettings() {
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        DrawDateTime drawDateTime = DrawDateTime.builder()
                .timeInterval(4)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(LocalTime.of(6,2,0))
                .toTime(LocalTime.of(23,0,0))
                .daysOfWeek(daysOfWeek)
                .build();
        return GameTypeSettings.builder()
                .gameType(GameType.KENO)
                .drawDateTime(drawDateTime)
                .minAmountOfMainNumbers(1)
                .maxAmountOfMainNumbers(10)
                .minValueOfMainNumbers(1)
                .maxValueOfMainNumbers(70)
                .minAmountOfExtraNumbers(0)
                .maxAmountOfExtraNumbers(0)
                .minValueOfExtraNumbers(0)
                .maxValueOfExtraNumbers(0)
                .build();
    }

}
