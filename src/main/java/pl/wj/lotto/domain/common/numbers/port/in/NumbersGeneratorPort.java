package pl.wj.lotto.domain.common.numbers.port.in;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.Numbers;

public interface NumbersGeneratorPort {
    Numbers generate(GameType gameType);
}
