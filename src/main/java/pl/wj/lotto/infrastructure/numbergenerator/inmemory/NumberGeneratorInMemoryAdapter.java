package pl.wj.lotto.infrastructure.numbergenerator.inmemory;

import pl.wj.lotto.domain.common.numbergenerator.NumberGeneratorPort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGeneratorInMemoryAdapter implements NumberGeneratorPort {
    @Override
    public List<Integer> generate(int lowerBound, int upperBound, int amount) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            numbers.add(next(lowerBound, upperBound));
        }
        return numbers;
    }

    private int next(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }
}
