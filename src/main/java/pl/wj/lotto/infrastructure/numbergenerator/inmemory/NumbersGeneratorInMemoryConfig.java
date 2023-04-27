package pl.wj.lotto.infrastructure.numbergenerator.inmemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.numbersgenerator.NumbersGeneratorPort;

@Configuration
@Profile("in-memory")
public class NumbersGeneratorInMemoryConfig {
    @Bean
    public NumbersGeneratorPort numberGeneratorPort() {
        return new NumbersGeneratorInMemoryAdapter();
    }
}
