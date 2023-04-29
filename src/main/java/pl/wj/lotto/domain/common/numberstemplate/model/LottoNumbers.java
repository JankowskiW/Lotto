package pl.wj.lotto.domain.common.numberstemplate.model;

import pl.wj.lotto.domain.common.drawingtype.DrawingTime;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.NumbersValidatable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LottoNumbers implements NumbersValidatable, NumbersTemplate {
    private static final int MAIN_NUMBERS_AMOUNT = 6;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 49;
    private static final DrawingTime DRAWING_TIME;

    private List<Integer> mainNumbers = new ArrayList<>();

    static {
        LocalTime time = LocalTime.of(22, 0);
        DRAWING_TIME = DrawingTime.builder()
                .timeInterval(0)
                .timeIntervalUnit(TimeUnit.DAYS)
                .fromTime(time)
                .toTime(time)
                .daysOfWeek(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY))
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
    public LocalDateTime getNextDrawingTime() {
        return getNextDrawingTime(DRAWING_TIME);
    }
}
