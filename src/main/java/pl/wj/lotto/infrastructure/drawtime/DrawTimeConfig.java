package pl.wj.lotto.infrastructure.drawtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.drawtime.DrawTimeChecker;

import java.time.Clock;

@Configuration
public class DrawTimeConfig {
    @Bean
    public DrawTimeChecker drawTimeChecker(Clock clock) {
        return new DrawTimeChecker(clock);
    }
}
