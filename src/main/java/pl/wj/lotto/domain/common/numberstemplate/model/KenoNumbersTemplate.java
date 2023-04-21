package pl.wj.lotto.domain.common.numberstemplate.model;

import java.util.List;

public class KenoNumbersTemplate extends NumbersTemplate {
    private static final int MAIN_MIN_NUMBERS_AMOUNT = 1;
    private static final int MAIN_MAX_NUMBERS_AMOUNT = 10;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 70;
    private final int mainMinNumbersAmount;
    private final int mainMaxNumbersAmount;
    public KenoNumbersTemplate() {
        super(MAIN_NUMBERS_MIN_VALUE, MAIN_NUMBERS_MAX_VALUE);
        this.mainMinNumbersAmount = MAIN_MIN_NUMBERS_AMOUNT;
        this.mainMaxNumbersAmount = MAIN_MAX_NUMBERS_AMOUNT;
    }
    public void setMainNumbers(List<Integer> mainNumbers) {
        if (mainNumbers.size() < mainMinNumbersAmount || mainNumbers.size() > mainMaxNumbersAmount)
            throw new RuntimeException(String.format(NUMBERS_AMOUNT_EXCEPTION_MESSAGE, "main"));
        super.setMainNumbers(mainNumbers);
    }
}
