package pl.wj.lotto.infrastructure.numbersreceiver.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

@Configuration
@Profile("!fake")
public class NumbersReceiverHttpConfig {
    @Bean
    public NumbersReceiverPort numberGeneratorPort() {
        return new NumbersReceiverHttpAdapter(null, "", 0);
    }
}
