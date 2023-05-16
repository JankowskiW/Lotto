package pl.wj.lotto.infrastructure.security.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.security")
public record SecurityProperties(String secretKey, long expirationDays, String issuer) {
}
