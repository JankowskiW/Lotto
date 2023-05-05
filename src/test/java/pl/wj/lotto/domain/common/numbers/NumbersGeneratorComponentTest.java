package pl.wj.lotto.domain.common.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettings;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.infrastructure.numbersreceiver.fake.NumbersReceiverFakeAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class NumbersGeneratorComponentTest {
    private NumbersGeneratorPort numbersReceiverPort;

    @BeforeEach
    void setUp() {
        numbersReceiverPort = new NumbersGenerator(new NumbersReceiverFakeAdapter());
    }

    @Test
    void shouldReturnSortedMainAndExtraNumbers() {
        // given
        GameType gameType = GameType.EJP;
        GameTypeSettings settings = GameTypeSettingsContainer.getGameTypeSettings(gameType);
        boolean sorted = true;

        // when
        Numbers result = numbersReceiverPort.generate(gameType, sorted);

        // then
        assertAll(
                () -> assertThat(result.gameType())
                        .usingRecursiveComparison()
                        .isEqualTo(gameType),
                () -> assertThat(result.drawDateTimeSettings())
                        .usingRecursiveComparison()
                        .isEqualTo(settings.drawDateTimeSettings()),
                () -> assertThat(result.mainNumbers())
                        .hasSize(settings.minAmountOfMainNumbers()),
                () -> assertThat(result.mainNumbers())
                        .allMatch(n -> (n >= settings.minValueOfMainNumbers() || n <= settings.maxValueOfMainNumbers())),
                () -> assertThat(result.extraNumbers())
                        .hasSize(settings.minAmountOfExtraNumbers()),
                () -> assertThat(result.extraNumbers())
                        .allMatch(n -> (n >= settings.minValueOfExtraNumbers() || n <= settings.maxValueOfExtraNumbers()))
        );
    }
}