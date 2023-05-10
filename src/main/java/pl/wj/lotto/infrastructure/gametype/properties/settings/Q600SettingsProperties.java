package pl.wj.lotto.infrastructure.gametype.properties.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.game-type.config.settings.q600")
public class Q600SettingsProperties extends GameTypeSettingsProperties {}
