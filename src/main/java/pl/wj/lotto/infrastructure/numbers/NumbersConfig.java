package pl.wj.lotto.infrastructure.numbers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.numbers.NumbersGenerator;
import pl.wj.lotto.domain.common.numbers.NumbersValidator;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

@Configuration
public class NumbersConfig {
    @Bean
    public NumbersGeneratorPort numbersGeneratorPort(NumbersReceiverPort numbersReceiverPort) {
        return new NumbersGenerator(numbersReceiverPort);
    }

    @Bean
    public NumbersValidatorPort numbersValidatorPort() {
        return new NumbersValidator();
    }
}
