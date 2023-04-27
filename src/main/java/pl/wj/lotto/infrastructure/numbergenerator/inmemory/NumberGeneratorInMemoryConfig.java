package pl.wj.lotto.infrastructure.numbergenerator.inmemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.numbergenerator.NumberGeneratorPort;

@Configuration
@Profile("in-memory")
public class NumberGeneratorInMemoryConfig {
    @Bean
    public NumberGeneratorPort numberGeneratorPort() {
        return new NumberGeneratorInMemoryAdapter();
    }
}
