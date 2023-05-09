package pl.wj.lotto.infrastructure.drawdatetime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.drawdatetime.DrawDateTimeChecker;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;

import java.time.Clock;

@Configuration
public class DrawDateTimeConfig {
    @Bean
    public DrawDateTimeCheckerPort drawDateTimeCheckerPort(Clock clock, GameTypeSettingsContainer gameTypeSettingsContainer) {
        return new DrawDateTimeChecker(clock, gameTypeSettingsContainer);
    }
}
