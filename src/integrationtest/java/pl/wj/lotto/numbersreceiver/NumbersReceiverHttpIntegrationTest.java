package pl.wj.lotto.numbersreceiver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.wj.lotto.BaseIntegrationTest;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class NumbersReceiverHttpIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private NumbersReceiverPort numbersReceiverPort;

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 50;
    private static final int AMOUNT = 6;

    @Test
    void shouldReturnGeneratedNumbers() {
        // given && when
        List<Integer> result = numbersReceiverPort.receive(LOWER_BOUND, UPPER_BOUND, AMOUNT);

        // then
        assertThat(result)
                .isNotNull()
                .containsAnyElementsOf(IntStream.rangeClosed(LOWER_BOUND, UPPER_BOUND).boxed().toList())
                .hasSize(AMOUNT);
    }
}
