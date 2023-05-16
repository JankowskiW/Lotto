package pl.wj.lotto.infrastructure.application.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.infrastructure.gametype.properties.interval.GameTypeIntervalProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.EjpSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.KenoSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.LottoSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.Q600SettingsProperties;
import pl.wj.lotto.infrastructure.numbersreceiver.http.NumbersRetrieverHttpConfigProperties;
import pl.wj.lotto.infrastructure.security.properties.SecurityProperties;

@Configuration
@EnableConfigurationProperties({
        NumbersRetrieverHttpConfigProperties.class,
        LottoSettingsProperties.class,
        Q600SettingsProperties.class,
        EjpSettingsProperties.class,
        KenoSettingsProperties.class,
        GameTypeIntervalProperties.class,
        SecurityProperties.class
})
public class PropertiesConfig {
}
