package pl.wj.lotto.infrastructure.numbergenerator.http;

import pl.wj.lotto.domain.common.numbersgenerator.NumbersGeneratorPort;

import java.util.List;

public class NumbersGeneratorHttpAdapter implements NumbersGeneratorPort {
    @Override
    public List<Integer> generate(int lowerBound, int upperBound, int amount) {
        return List.of();
    }
}
