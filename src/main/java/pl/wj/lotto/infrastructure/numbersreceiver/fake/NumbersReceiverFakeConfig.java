package pl.wj.lotto.infrastructure.numbersreceiver.fake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

@Configuration
@Profile("fake")
public class NumbersReceiverFakeConfig {
    @Bean
    public NumbersReceiverPort numbersReceiverPort() {
        return new NumbersReceiverFakeAdapter();
    }
}
