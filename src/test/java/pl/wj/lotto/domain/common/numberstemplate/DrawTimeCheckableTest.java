package pl.wj.lotto.domain.common.numberstemplate;

import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawtime.DrawTimeCheckable;
import pl.wj.lotto.domain.common.drawtime.model.DrawTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DrawTimeCheckableTest {

    @Test
    void shouldReturnNextDrawTimeInTheSameDayWhenThereIsMoreThanOneDrawOnThatDay() {
        // given
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        LocalDate currentDate = LocalDate.of(2023,1,1);
        LocalTime nextDrawTime = LocalTime.of(10, 20);
        DrawTime drawTime = DrawTime.builder()
                .timeInterval(10)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(LocalTime.of(6,0))
                .toTime(LocalTime.of(23,0))
                .daysOfWeek(daysOfWeek)
                .build();
        DrawTimeCheckable drawTimeCheckable = createDrawTimeCheckableImpl(drawTime);
        LocalDateTime expectedResult = LocalDateTime.of(currentDate, nextDrawTime);

        // when
        LocalDateTime result = drawTimeCheckable.getNextDrawTime();

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    private DrawTimeCheckable createDrawTimeCheckableImpl(DrawTime drawTime) {
        return new DrawTimeCheckable() {
            @Override
            public LocalDateTime getNextDrawTime() {
                return getNextDrawTime(drawTime);
            }
        };
    }
}