package pl.wj.lotto.infrastructure.gametype.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettings;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.infrastructure.drawinitiator.scheduler.config.IntervalConfigProperties;
import pl.wj.lotto.infrastructure.gametype.properties.GameTypeSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.LottoSettingsProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GameTypeSettingsConfig {
    private final IntervalConfigProperties intervalProperties;
    private final GameTypeSettingsProperties lottoSettingsProperties;
    private final GameTypeSettingsProperties q600SettingsProperties;
    private final GameTypeSettingsProperties ejpSettingsProperties;
    private final GameTypeSettingsProperties kenoSettingsProperties;

    @Bean
    public GameTypeSettingsContainer gameTypeSettingsContainer() {
        return GameTypeSettingsContainer.builder()
                .settings(prepareSettings())
                .build();
    }

    private  Map<GameType, GameTypeSettings> prepareSettings() {
        Map<GameType, GameTypeSettings> settings = new HashMap<>();
        settings.put(GameType.LOTTO, createLottoSettings());
        settings.put(GameType.Q600, createQuick600Settings());
        settings.put(GameType.EJP, createEurojackpotSettings());
        settings.put(GameType.KENO, createKenoSettings());
        return settings;
    }

    private GameTypeSettings createLottoSettings() {
        return GameTypeSettings.builder()
                .interval(intervalProperties.lotto())
                .settings(lottoSettingsProperties)
                .build();
    }

    private GameTypeSettings createQuick600Settings() {
        return GameTypeSettings.builder()
                .interval(intervalProperties.q600())
                .settings(q600SettingsProperties)
                .build();
    }

    private GameTypeSettings createEurojackpotSettings() {
        return GameTypeSettings.builder()
                .interval(intervalProperties.ejp())
                .settings(ejpSettingsProperties)
                .build();
    }

    private GameTypeSettings createKenoSettings() {
        return GameTypeSettings.builder()
                .interval(intervalProperties.keno())
                .settings(kenoSettingsProperties)
                .build();
    }
}
