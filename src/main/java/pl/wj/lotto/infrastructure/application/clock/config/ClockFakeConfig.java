package pl.wj.lotto.infrastructure.application.clock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.infrastructure.application.clock.AdjustableClock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Configuration
@Profile("fake")
public class ClockFakeConfig {
    @Bean
    public AdjustableClock clock() {
        LocalDate date = LocalDate.of(2023, 1, 5);
        LocalTime time = LocalTime.of(12,0,0);
        return AdjustableClock.ofLocalDateAndLocalTime(date, time, ZoneId.systemDefault());
    }
}
