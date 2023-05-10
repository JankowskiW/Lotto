package pl.wj.lotto.infrastructure.gametype.properties.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.game-type.config.settings.keno")
public class KenoSettingsProperties extends GameTypeSettingsProperties {}
