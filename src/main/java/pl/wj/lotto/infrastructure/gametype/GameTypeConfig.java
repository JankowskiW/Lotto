package pl.wj.lotto.infrastructure.gametype;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.infrastructure.gametype.properties.interval.GameTypeIntervalProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GameTypeConfig {
    @Bean
    public GameTypeSettingsContainer gameTypeSettingsContainer1(
            LottoSettingsProperties lottoSettingsProperties,
            Q600SettingsProperties q600SettingsProperties,
            EjpSettingsProperties ejpSettingsProperties,
            KenoSettingsProperties kenoSettingsProperties,
            GameTypeIntervalProperties gameTypeIntervalProperties) {

        Map<GameType, GameTypeSettingsProperties> settings = new HashMap<>();
        settings.put(GameType.LOTTO, lottoSettingsProperties);
        settings.put(GameType.Q600, q600SettingsProperties);
        settings.put(GameType.EJP, ejpSettingsProperties);
        settings.put(GameType.KENO, kenoSettingsProperties);

        Map<GameType, String> intervals = new HashMap<>();
        intervals.put(GameType.LOTTO, gameTypeIntervalProperties.lotto());
        intervals.put(GameType.Q600, gameTypeIntervalProperties.q600());
        intervals.put(GameType.EJP, gameTypeIntervalProperties.ejp());
        intervals.put(GameType.KENO, gameTypeIntervalProperties.keno());

        return GameTypeSettingsContainer.builder()
                .intervals(intervals)
                .settings(settings)
                .build();
    }
}
