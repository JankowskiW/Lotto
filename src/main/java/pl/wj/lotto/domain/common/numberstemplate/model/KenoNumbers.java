package pl.wj.lotto.domain.common.numberstemplate.model;

import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.NumbersValidatable;

import java.util.ArrayList;
import java.util.List;

public class KenoNumbers implements NumbersValidatable, NumbersTemplate {
    private static final int MAIN_MIN_NUMBERS_AMOUNT = 1;
    private static final int MAIN_MAX_NUMBERS_AMOUNT = 10;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 70;

    private List<Integer> mainNumbers = new ArrayList<>();

    @Override
    public boolean validate() {
        return validateMainNumbersAmount() &&
                validateMainNumbersValues();
    }

    private boolean validateMainNumbersAmount() {
        return mainNumbers.size() >= MAIN_MIN_NUMBERS_AMOUNT && mainNumbers.size() <= MAIN_MAX_NUMBERS_AMOUNT;
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
}
