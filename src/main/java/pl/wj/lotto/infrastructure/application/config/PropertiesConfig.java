package pl.wj.lotto.infrastructure.application.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.infrastructure.drawinitiator.scheduler.config.IntervalConfigProperties;
import pl.wj.lotto.infrastructure.gametype.properties.EjpSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.KenoSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.LottoSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.Q600SettingsProperties;
import pl.wj.lotto.infrastructure.numbersreceiver.http.NumbersRetrieverHttpConfigProperties;

@Configuration
@EnableConfigurationProperties({
        NumbersRetrieverHttpConfigProperties.class,
        IntervalConfigProperties.class,
        LottoSettingsProperties.class,
        Q600SettingsProperties.class,
        EjpSettingsProperties.class,
        KenoSettingsProperties.class})
public class PropertiesConfig {
}
