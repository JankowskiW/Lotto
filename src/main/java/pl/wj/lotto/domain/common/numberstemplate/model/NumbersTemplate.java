package pl.wj.lotto.domain.common.numberstemplate.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class NumbersTemplate {
    protected static final String NUMBERS_VALUE_EXCEPTION_MESSAGE = "%s numbers should be in range %d and %d";
    protected static final String NUMBERS_AMOUNT_EXCEPTION_MESSAGE = "Wrong %s numbers amount";
    private final int drawingTypeId;
    private final int mainNumbersAmount;
    private final int mainNumbersMinValue;
    private final int mainNumbersMaxValue;
    @Getter @Setter
    private List<Integer> mainNumbers;

    public NumbersTemplate(int drawingTypeId, int mainNumbersMinValue, int mainNumbersMaxValue) {
        this(drawingTypeId, 0, mainNumbersMinValue, mainNumbersMaxValue);
    }

    public NumbersTemplate(int drawingTypeId, int mainNumbersAmount, int mainNumbersMinValue, int mainNumbersMaxValue) {
        this.drawingTypeId = drawingTypeId;
        this.mainNumbersAmount = mainNumbersAmount;
        this.mainNumbersMinValue = mainNumbersMinValue;
        this.mainNumbersMaxValue = mainNumbersMaxValue;
    }

    public void setMainNumbers(List<Integer> mainNumbers) {
        if (mainNumbersAmount > 0 && mainNumbers.size() != mainNumbersAmount)
            throw new RuntimeException(String.format(NUMBERS_AMOUNT_EXCEPTION_MESSAGE, "main"));
        if(mainNumbers.stream() .anyMatch(n -> (n < mainNumbersMinValue || n > mainNumbersMaxValue)))
            throw new RuntimeException(String.format(NUMBERS_VALUE_EXCEPTION_MESSAGE, "main", mainNumbersMinValue, mainNumbersMaxValue));
        this.setMainNumbers(mainNumbers);
    }
}
