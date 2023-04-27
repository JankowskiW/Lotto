package pl.wj.lotto.infrastructure.numbergenerator.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.numbergenerator.NumberGeneratorPort;

@Configuration
public class NumberGeneratorHttpConfig {
    @Bean
    public NumberGeneratorPort numberGeneratorPort() {
        return new NumberGeneratorHttpAdapter();
    }
}
