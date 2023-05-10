package pl.wj.lotto.infrastructure.gametype.properties.interval;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.game-type.config.interval")
public record GameTypeIntervalProperties (
        String lotto,
        String q600,
        String ejp,
        String keno
) {}
