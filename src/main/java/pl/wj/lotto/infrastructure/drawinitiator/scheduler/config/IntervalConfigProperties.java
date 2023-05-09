package pl.wj.lotto.infrastructure.drawinitiator.scheduler.config;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.draw-initiator.config.interval")
public record IntervalConfigProperties (
        String lotto,
        String q600,
        String ejp,
        String keno
){}
