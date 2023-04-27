package pl.wj.lotto.domain.common.numbergenerator;

import java.util.List;

public interface NumberGeneratorPort {
    List<Integer> generate(int lowerBound, int upperBound, int amount);
}
