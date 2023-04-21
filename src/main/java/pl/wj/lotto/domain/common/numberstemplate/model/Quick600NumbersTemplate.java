package pl.wj.lotto.domain.common.numberstemplate.model;

public class Quick600NumbersTemplate extends NumbersTemplate {
    private static final int MAIN_NUMBERS_AMOUNT = 6;
    private static final int MAIN_NUMBERS_MIN_VALUE = 1;
    private static final int MAIN_NUMBERS_MAX_VALUE = 32;
    public Quick600NumbersTemplate() {
        super(MAIN_NUMBERS_AMOUNT, MAIN_NUMBERS_MIN_VALUE, MAIN_NUMBERS_MAX_VALUE);
    }
}
