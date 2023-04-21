package pl.wj.lotto.domain.common.numberstemplate.model;

import pl.wj.lotto.domain.drawing.model.DrawingType;

public class LottoNumbersTemplate extends NumbersTemplate {
    private static final int MAIN_NUMBERS_AMOUNT = 6;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 49;
    public LottoNumbersTemplate() {
        super(DrawingType.LOTTO.getId(), MAIN_NUMBERS_AMOUNT, MAIN_NUMBERS_MIN_VALUE, MAIN_NUMBERS_MAX_VALUE);
    }
}
