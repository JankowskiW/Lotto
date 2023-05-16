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
        int approachNumber = 0;
        for (int i = 1; i <= amount; i++) {
            do {
                approachNumber++;
                if (approachNumber > APPROACH_LIMIT) {
                    String message = String.format("Cannot generate numbers ([%d - %d] x %d), try again.", lowerBound, upperBound, amount);
                    log.error(message);
                    throw new NumbersReceiverException(message);
                }
                nextNumber = next(lowerBound, upperBound);
            } while(numbers.contains(nextNumber));
            numbers.add(nextNumber);
        }
        return numbers;
    }

    private int next(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }
}
