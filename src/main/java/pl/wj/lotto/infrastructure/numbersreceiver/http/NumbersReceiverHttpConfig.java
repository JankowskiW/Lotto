package pl.wj.lotto.infrastructure.numbersreceiver.http;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.infrastructure.application.exception.handler.RestTemplateResponseErrorHandler;

import java.time.Duration;

@Configuration
@Profile("!fake")
@RequiredArgsConstructor
public class NumbersReceiverHttpConfig {
    private final NumbersRetrieverHttpConfigProperties properties;

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    @Bean
    public NumbersReceiverPort numberGeneratorPort(RestTemplate restTemplate) {
        return new NumbersReceiverHttpAdapter(restTemplate, properties.uri(), properties.port());
    }
}
