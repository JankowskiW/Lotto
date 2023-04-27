package pl.wj.lotto.domain.common.numbersgenerator;

import java.util.List;

public interface NumbersGeneratorPort {
    List<Integer> generate(int lowerBound, int upperBound, int amount);
}
