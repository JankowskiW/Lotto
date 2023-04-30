package pl.wj.lotto.infrastructure.numbersreceiver.fake;

import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NumbersReceiverFakeAdapter implements NumbersReceiverPort {
    @Override
    public List<Integer> receive(int lowerBound, int upperBound, int amount) {
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
