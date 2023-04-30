package pl.wj.lotto.domain.common.numbersreceiver;

import java.util.List;

public interface NumbersReceiverPort {
    List<Integer> receive(int lowerBound, int upperBound, int amount);
}
