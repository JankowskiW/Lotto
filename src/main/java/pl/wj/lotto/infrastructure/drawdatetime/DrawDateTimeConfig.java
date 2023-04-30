package pl.wj.lotto.infrastructure.drawdatetime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.drawdatetime.DrawDateTimeChecker;

import java.time.Clock;

@Configuration
public class DrawDateTimeConfig {
    @Bean
    public DrawDateTimeChecker drawDateTimeChecker(Clock clock) {
        return new DrawDateTimeChecker(clock);
    }
}
