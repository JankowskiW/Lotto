package pl.wj.lotto.infrastructure.gametype.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.game-type.config.settings.ejp")
public class EjpSettingsProperties extends GameTypeSettingsProperties {
    int mainNumbersAmount;
    int mainNumbersMinValue;
    int mainNumbersMaxValue;
    int extraNumbersAmount;
    int extraNumbersMinValue;
    int extraNumbersMaxValue;
}
