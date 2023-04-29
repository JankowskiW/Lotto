package pl.wj.lotto.domain.common.numberstemplate;

import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawingtype.DrawingTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DrawingTimeCheckableTest {

    @Test
    void shouldReturnNextDrawingTimeInTheSameDayWhenThereIsMoreThanOneDrawingOnThatDay() {
        // given
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        LocalDate currentDate = LocalDate.of(2023,1,1);
        LocalTime nextDrawingTime = LocalTime.of(10, 20);
        DrawingTime drawingTime = DrawingTime.builder()
                .timeInterval(10)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(LocalTime.of(6,0))
                .toTime(LocalTime.of(23,0))
                .daysOfWeek(daysOfWeek)
                .build();
        DrawingTimeCheckable drawingTimeCheckable = createDrawingTimeCheckableImpl(drawingTime);
        LocalDateTime expectedResult = LocalDateTime.of(currentDate, nextDrawingTime);

        // when
        LocalDateTime result = drawingTimeCheckable.getNextDrawingTime();

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    private DrawingTimeCheckable createDrawingTimeCheckableImpl(DrawingTime drawingTime) {
        return new DrawingTimeCheckable() {
            @Override
            public LocalDateTime getNextDrawingTime() {
                return getNextDrawingTime(drawingTime);
            }
        };
    }
}