package pl.wj.lotto.domain.common.numberstemplate.model;

import lombok.Getter;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.NumbersValidatable;

import java.util.ArrayList;
import java.util.List;

public class EuroJackpotNumbers implements NumbersValidatable, NumbersTemplate {
    private static final int MAIN_NUMBERS_AMOUNT = 5;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 50;
    private static final int EXTRA_NUMBERS_AMOUNT = 2;
    private static final int EXTRA_NUMBERS_MIN_VALUE = 1;
    private static final int EXTRA_NUMBERS_MAX_VALUE = 12;

    @Getter
    private List<Integer> mainNumbers = new ArrayList<>();
    @Getter
    private List<Integer> extraNumbers = new ArrayList<>();

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
}
