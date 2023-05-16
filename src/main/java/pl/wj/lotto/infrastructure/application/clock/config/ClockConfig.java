package pl.wj.lotto.infrastructure.application.clock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@Configuration
@Profile("!fake")
public class ClockConfig {
    @Bean
    public Clock clock () {
        return Clock.systemUTC();
    }
}
