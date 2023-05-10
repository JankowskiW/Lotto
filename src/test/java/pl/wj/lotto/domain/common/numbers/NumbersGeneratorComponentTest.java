package pl.wj.lotto.domain.common.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.infrastructure.gametype.GameTypeConfig;
import pl.wj.lotto.infrastructure.gametype.properties.interval.GameTypeIntervalProperties;
import pl.wj.lotto.infrastructure.gametype.properties.settings.*;
import pl.wj.lotto.infrastructure.numbersreceiver.fake.NumbersReceiverFakeAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class NumbersGeneratorComponentTest {
    private NumbersGeneratorPort numbersReceiverPort;
    private GameTypeSettingsContainer gameTypeSettingsContainer;

    @BeforeEach
    void setUp() {
        LottoSettingsProperties lottoSettingsProperties = new LottoSettingsProperties();
        Q600SettingsProperties q600SettingsProperties = new Q600SettingsProperties();
        EjpSettingsProperties ejpSettingsProperties = new EjpSettingsProperties();
        KenoSettingsProperties kenoSettingsProperties = new KenoSettingsProperties();
        GameTypeIntervalProperties gameTypeIntervalProperties = GameTypeIntervalProperties.builder().build();
        gameTypeSettingsContainer = new GameTypeConfig().gameTypeSettingsContainer1(
                lottoSettingsProperties, q600SettingsProperties, ejpSettingsProperties, kenoSettingsProperties, gameTypeIntervalProperties);
        numbersReceiverPort = new NumbersGenerator(new NumbersReceiverFakeAdapter(), gameTypeSettingsContainer);
    }

    @Test
    void shouldReturnSortedMainAndExtraNumbers() {
        // given
        GameType gameType = GameType.EJP;
        GameTypeSettingsProperties settings = gameTypeSettingsContainer.settings().get(gameType);
        boolean sorted = true;

        // when
        Numbers result = numbersReceiverPort.generate(gameType, sorted);

        // then
        assertAll(
                () -> assertThat(result.gameType())
                        .usingRecursiveComparison()
                        .isEqualTo(gameType),
//                () -> assertThat(result.drawDateTimeSettings())
//                        .usingRecursiveComparison()
//                        .isEqualTo(settings.drawDateTimeSettings()),// TODO: Replace settings.drawDateTime to method from new DrawDateTimeChecker with CronTriggers
                () -> assertThat(result.mainNumbers())
                        .hasSize(settings.getMainNumbersAmount()),
                () -> assertThat(result.mainNumbers())
                        .allMatch(n -> (n >= settings.getMainNumbersMinValue() || n <= settings.getMainNumbersMaxValue())),
                () -> assertThat(result.extraNumbers())
                        .hasSize(settings.getExtraNumbersAmount()),
                () -> assertThat(result.extraNumbers())
                        .allMatch(n -> (n >= settings.getExtraNumbersMinValue() || n <= settings.getExtraNumbersMaxValue()))
        );
    }
}