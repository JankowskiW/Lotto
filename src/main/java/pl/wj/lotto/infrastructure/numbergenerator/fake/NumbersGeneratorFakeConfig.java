package pl.wj.lotto.infrastructure.numbergenerator.fake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.numbersgenerator.NumbersGeneratorPort;

@Configuration
@Profile("fake")
public class NumbersGeneratorFakeConfig {
    @Bean
    public NumbersGeneratorPort numberGeneratorPort() {
        return new NumbersGeneratorFakeAdapter();
    }
}
