package pl.wj.lotto.domain.common.numberstemplate.model;

import pl.wj.lotto.domain.common.drawingtype.DrawingTime;
import pl.wj.lotto.domain.common.numberstemplate.DrawingTimeCheckable;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.NumbersValidatable;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Quick600Numbers implements NumbersValidatable, NumbersTemplate, DrawingTimeCheckable {
    private static final int MAIN_NUMBERS_AMOUNT = 6;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 32;
    private static final DrawingTime DRAWING_TIME;

    private List<Integer> mainNumbers = new ArrayList<>();

    static {
        List<DayOfWeek> daysOfWeek = Arrays.stream(DayOfWeek.values()).toList();
        DRAWING_TIME = DrawingTime.builder()
                .timeInterval(4)
                .timeIntervalUnit(TimeUnit.MINUTES)
                .fromTime(LocalTime.of(6,2))
                .toTime(LocalTime.of(23,52))
                .daysOfWeek(daysOfWeek)
                .build();
    }

    @Override
    public boolean validate() {
        return validateMainNumbersAmount() &&
                validateMainNumbersValues();
    }

    private boolean validateMainNumbersAmount() {
        return mainNumbers.size() == MAIN_NUMBERS_AMOUNT;
    }

    private boolean validateMainNumbersValues() {
        return mainNumbers.stream().noneMatch(n -> (n < MAIN_NUMBERS_MIN_VALUE || n > MAIN_NUMBERS_MAX_VALUE));
    }

    @Override
    public void setNumbers(List<Integer> mainNumbers, List<Integer> extraNumbers) {
        this.mainNumbers = new ArrayList<>(mainNumbers);
        if (!validate()) {
            this.mainNumbers = new ArrayList<>();
            throw new RuntimeException("Invalid parameters");
        }
    }

    @Override
    public List<Integer> getMainNumbers() {
        return mainNumbers;
    }

    @Override
    public DrawingTime getNextDrawingTime() {
        return DRAWING_TIME;
    }
}
