package pl.wj.lotto.infrastructure.numbersreceiver.http;

import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

import java.util.List;

public class NumbersReceiverHttpAdapter implements NumbersReceiverPort {
    @Override
    public List<Integer> receive(int lowerBound, int upperBound, int amount) {
        return List.of();
    }
}
