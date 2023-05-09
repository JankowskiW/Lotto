package pl.wj.lotto.infrastructure.gametype.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.game-type.config.settings.q600")
public class Q600SettingsProperties extends GameTypeSettingsProperties {
    int mainNumbersAmount;
    int mainNumbersMinValue;
    int mainNumbersMaxValue;
}