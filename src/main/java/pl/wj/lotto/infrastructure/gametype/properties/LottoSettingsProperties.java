package pl.wj.lotto.infrastructure.gametype.properties;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.game-type.config.settings.lotto")
public class LottoSettingsProperties extends GameTypeSettingsProperties {
    int mainNumbersAmount;
    int mainNumbersMinValue;
    int mainNumbersMaxValue;
}