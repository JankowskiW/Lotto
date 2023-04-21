package pl.wj.lotto.domain.common.numberstemplate.model;

import lombok.Getter;
import lombok.Setter;
import pl.wj.lotto.domain.drawing.model.DrawingType;

import java.util.List;

public class EuroJackpotNumbersTemplate extends NumbersTemplate{
    private static final int MAIN_NUMBERS_AMOUNT = 5;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 50;
    private static final int EXTRA_NUMBERS_AMOUNT = 2;
    private static final int EXTRA_NUMBERS_MIN_VALUE = 1;
    private static final int EXTRA_NUMBERS_MAX_VALUE = 12;
    private final int extraNumbersAmount;
    private final int extraNumbersMinValue;
    private final int extraNumbersMaxValue;
    @Getter @Setter
    private List<Integer> extraNumbers;
    public EuroJackpotNumbersTemplate() {
        super(DrawingType.EJP.getId(), MAIN_NUMBERS_AMOUNT, MAIN_NUMBERS_MIN_VALUE, MAIN_NUMBERS_MAX_VALUE);
        this.extraNumbersAmount = EXTRA_NUMBERS_AMOUNT;
        this.extraNumbersMinValue = EXTRA_NUMBERS_MIN_VALUE;
        this.extraNumbersMaxValue = EXTRA_NUMBERS_MAX_VALUE;
    }
    public void setExtraNumbers(List<Integer> extraNumbers) {
        if (extraNumbers.size() != extraNumbersAmount)
            throw new RuntimeException(String.format(NUMBERS_AMOUNT_EXCEPTION_MESSAGE, "extra"));
        if(extraNumbers.stream().anyMatch(n -> (n < extraNumbersMinValue || n > extraNumbersMaxValue)))
            throw new RuntimeException(String.format(NUMBERS_VALUE_EXCEPTION_MESSAGE, "extra", extraNumbersMinValue, extraNumbersMaxValue));
        this.extraNumbers = extraNumbers;
    }
}
