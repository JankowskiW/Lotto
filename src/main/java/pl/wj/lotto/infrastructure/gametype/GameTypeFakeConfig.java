package pl.wj.lotto.infrastructure.gametype;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.infrastructure.gametype.properties.interval.GameTypeIntervalProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.EjpSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.GameTypeSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.KenoSettingsProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.LottoSettingsProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("fake")
public class GameTypeFakeConfig {
    @Bean
    public GameTypeSettingsContainer gameTypeSettingsContainer() {
        GameTypeIntervalProperties gameTypeIntervalProperties = gameTypeIntervalProperties();

        Map<GameType, GameTypeSettingsProperties> settings = new HashMap<>();
        settings.put(GameType.LOTTO, lottoSettingsProperties());
        settings.put(GameType.Q600, q600SettingsProperties());
        settings.put(GameType.EJP, ejpSettingsProperties());
        settings.put(GameType.KENO, kenoSettingsProperties());

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

    private GameTypeIntervalProperties gameTypeIntervalProperties() {
        return GameTypeIntervalProperties.builder()
                .lotto("0 0 22 * * 2,4,6")
                .q600("0 2-58/4 6-22 * * *")
                .ejp("0 0 21 * * 2,5")
                .keno("0 0-56/4 6-22 * * *")
                .build();
    }

    private GameTypeSettingsProperties lottoSettingsProperties() {
        GameTypeSettingsProperties lottoSettingsProperties = new LottoSettingsProperties();
        lottoSettingsProperties.setMainNumbersAmount(6);
        lottoSettingsProperties.setMainNumbersMinValue(1);
        lottoSettingsProperties.setMainNumbersMaxValue(49);
        return lottoSettingsProperties;
    }

    private GameTypeSettingsProperties q600SettingsProperties() {
        GameTypeSettingsProperties q600SettingsProperties = new LottoSettingsProperties();
        q600SettingsProperties.setMainNumbersAmount(6);
        q600SettingsProperties.setMainNumbersMinValue(1);
        q600SettingsProperties.setMainNumbersMaxValue(32);
        return q600SettingsProperties;
    }

    private GameTypeSettingsProperties ejpSettingsProperties() {
        GameTypeSettingsProperties ejpSettingsProperties = new EjpSettingsProperties();
        ejpSettingsProperties.setMainNumbersAmount(5);
        ejpSettingsProperties.setMainNumbersMinValue(1);
        ejpSettingsProperties.setMainNumbersMaxValue(50);
        ejpSettingsProperties.setExtraNumbersAmount(2);
        ejpSettingsProperties.setExtraNumbersMinValue(1);
        ejpSettingsProperties.setExtraNumbersMaxValue(12);
        return ejpSettingsProperties;
    }

    private GameTypeSettingsProperties kenoSettingsProperties() {
        GameTypeSettingsProperties kenoSettingsProperties = new KenoSettingsProperties();
        kenoSettingsProperties.setMainNumbersAmount(20);
        kenoSettingsProperties.setMainTicketNumbersMinAmount(1);
        kenoSettingsProperties.setMainTicketNumbersMaxAmount(10);
        kenoSettingsProperties.setMainNumbersMinValue(1);
        kenoSettingsProperties.setMainNumbersMaxValue(70);
        return kenoSettingsProperties;
    }
}
