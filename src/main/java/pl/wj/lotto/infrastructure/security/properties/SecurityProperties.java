package pl.wj.lotto.infrastructure.security.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.security")
@Builder
public record SecurityProperties(String secretKey, long expirationDays, String issuer) {
}
