package pl.wj.lotto.infrastructure.gametype.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.game-type.config.settings.keno")
public class KenoSettingsProperties extends GameTypeSettingsProperties {
    int mainNumbersMinAmount;
    int mainNumbersMaxAmount;
    int mainNumbersMinValue;
    int mainNumbersMaxValue;
}