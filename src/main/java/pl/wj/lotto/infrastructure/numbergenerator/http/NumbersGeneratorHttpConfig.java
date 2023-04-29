package pl.wj.lotto.infrastructure.numbergenerator.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.numbersgenerator.NumbersGeneratorPort;

@Configuration
@Profile("!fake")
public class NumbersGeneratorHttpConfig {
    @Bean
    public NumbersGeneratorPort numberGeneratorPort() {
        return new NumbersGeneratorHttpAdapter();
    }
}
