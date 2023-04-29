package pl.wj.lotto.domain.common.numberstemplate.model;

import pl.wj.lotto.domain.common.drawtime.model.DrawTime;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.NumbersValidatable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EuroJackpotNumbers implements NumbersValidatable, NumbersTemplate {
    private static final int MAIN_NUMBERS_AMOUNT = 5;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 50;
    private static final int EXTRA_NUMBERS_AMOUNT = 2;
    private static final int EXTRA_NUMBERS_MIN_VALUE = 1;
    private static final int EXTRA_NUMBERS_MAX_VALUE = 12;
    private static final DrawTime DRAW_TIME;

    private List<Integer> mainNumbers = new ArrayList<>();
    private List<Integer> extraNumbers = new ArrayList<>();

    static {
        LocalTime time = LocalTime.of(21, 0);
        DRAW_TIME = DrawTime.builder()
                .timeInterval(0)
                .timeIntervalUnit(TimeUnit.DAYS)
                .fromTime(time)
                .toTime(time)
                .daysOfWeek(List.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY))
                .build();
    }


    @Override
    public boolean validate() {
        return validateMainNumbersAmount() &&
                validateMainNumbersValues() &&
                validateExtraNumbersAmount() &&
                validateExtraNumbersValues();
    }

    private boolean validateMainNumbersAmount() {
        return mainNumbers.size() == MAIN_NUMBERS_AMOUNT;
    }

    private boolean validateMainNumbersValues() {
        return mainNumbers.stream().noneMatch(n -> (n < MAIN_NUMBERS_MIN_VALUE || n > MAIN_NUMBERS_MAX_VALUE));
    }

    private boolean validateExtraNumbersAmount() {
        return extraNumbers.size() == EXTRA_NUMBERS_AMOUNT;
    }

    private boolean validateExtraNumbersValues() {
        return extraNumbers.stream().noneMatch(n -> (n < EXTRA_NUMBERS_MIN_VALUE || n > EXTRA_NUMBERS_MAX_VALUE));
    }

    @Override
    public void setNumbers(List<Integer> mainNumbers, List<Integer> extraNumbers) {
        if (mainNumbers == null || extraNumbers == null) throw new RuntimeException("Invalid parameters");
        this.mainNumbers = new ArrayList<>(mainNumbers);
        this.extraNumbers = new ArrayList<>(extraNumbers);
        if (!validate()) {
            this.mainNumbers = new ArrayList<>();
            this.extraNumbers = new ArrayList<>();
            throw new RuntimeException("Invalid parameters");
        }
    }

    @Override
    public List<Integer> getMainNumbers() {
        return mainNumbers;
    }

    @Override
    public List<Integer> getExtraNumbers() {
        return extraNumbers;
    }


    @Override
    public LocalDateTime getNextDrawTime() {
        return getNextDrawTime(DRAW_TIME);
    }
}
