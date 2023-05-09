package pl.wj.lotto.infrastructure.numbersreceiver.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.http.client.config.http")
public record NumbersRetrieverHttpConfigProperties (
        int connectionTimeout,
        int readTimeout,
        String uri,
        int port
) {}
