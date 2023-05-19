package pl.wj.lotto.infrastructure.numbersreceiver.fake;

import lombok.extern.log4j.Log4j2;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.infrastructure.application.exception.definition.NumbersReceiverException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
public class NumbersReceiverFakeAdapter implements NumbersReceiverPort {

    private static final int APPROACH_LIMIT = 10;

    @Override
    public List<Integer> receive(int lowerBound, int upperBound, int amount) {
        List<Integer> numbers = new ArrayList<>();
        int nextNumber;
        for (int i = 1; i <= amount; i++) {
            int approachNumber = 0;
            do {
                approachNumber++;
                if (approachNumber > APPROACH_LIMIT) {
                    String message = String.format("Cannot generate numbers ([%d - %d] x %d)", lowerBound, upperBound, amount);
                    throw new NumbersReceiverException(message);
                }
                nextNumber = next(lowerBound, upperBound);
                log.info(numbers);
                log.info(nextNumber);
                log.info(numbers.contains(nextNumber));
            } while(numbers.contains(nextNumber));
            numbers.add(nextNumber);
        }
        return numbers;
    }

    private int next(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }
}
