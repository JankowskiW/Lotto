package pl.wj.lotto.infrastructure.application.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.infrastructure.numbers.numbersreceiver.http.NumbersRetrieverHttpConfigProperties;

@Configuration
@EnableConfigurationProperties({NumbersRetrieverHttpConfigProperties.class})
public class PropertiesConfig {
}
