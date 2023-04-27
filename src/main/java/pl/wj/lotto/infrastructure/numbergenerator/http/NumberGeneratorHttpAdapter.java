package pl.wj.lotto.infrastructure.numbergenerator.http;

import pl.wj.lotto.domain.common.numbergenerator.NumberGeneratorPort;

import java.util.List;

public class NumberGeneratorHttpAdapter implements NumberGeneratorPort {
    @Override
    public List<Integer> generate(int lowerBound, int upperBound, int amount) {
        return List.of();
    }
}
