package pl.wj.lotto.infrastructure.drawinitiator.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="lotto.scheduler.config.enabled", matchIfMissing = true)
public class SchedulerConfig {
}
