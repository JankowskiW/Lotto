package pl.wj.lotto.domain.common.numbers.port.in;

import pl.wj.lotto.domain.common.numbers.model.Numbers;

public interface NumbersValidatorPort {
    boolean validate(Numbers numbers);
}
