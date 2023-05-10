package pl.wj.lotto.domain.common.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.infrastructure.gametype.GameTypeFakeConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NumbersValidatorTest {
    private NumbersValidatorPort numbersValidatorPort;

    @BeforeEach
    void setUp() {
        GameTypeSettingsContainer gameTypeSettingsContainer = new GameTypeFakeConfig().gameTypeSettingsContainer();
        numbersValidatorPort = new NumbersValidator(gameTypeSettingsContainer);
    }

    @Test
    void shouldReturnFalseWhenEvenOneOfGivenNumbersOccursMoreThanOnce() {
        // given
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,6,6))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    // LOTTO
    @Test
    void shouldReturnTrueWhenGivenLottoNumbersAreValid() {
        // given
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenGivenLottoNumbersAmountIsInvalid() {
        // given
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnFalseWhenAtLeastOneOfGivenLottoNumbersValueIsInvalid() {
        // given
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,1000))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    // Q600
    @Test
    void shouldReturnTrueWhenGivenQuick600NumbersAreValid() {
        // given
        GameType gameType = GameType.Q600;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnTrueWhenGivenQuick600NumbersAmountIsInvalid() {
        // given
        GameType gameType = GameType.Q600;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAtLeastOneOfGivenQuick600NumbersValueIsInvalid() {
        // given
        GameType gameType = GameType.Q600;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,1000))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    // EJP
    @Test
    void shouldReturnTrueWhenGivenEurojackpotNumbersAreValid() {
        // given
        GameType gameType = GameType.EJP;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnTrueWhenGivenEurojackpotMainNumbersAmountIsInvalid() {
        // given
        GameType gameType = GameType.EJP;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4))
                .extraNumbers(List.of(1,2))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAtLeastOneOfGivenEurojackpotMainNumbersValueIsInvalid() {
        // given
        GameType gameType = GameType.EJP;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,1000))
                .extraNumbers(List.of(1,2))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenGivenEurojackpotExtraNumbersAmountIsInvalid() {
        // given
        GameType gameType = GameType.EJP;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAtLeastOneOfGivenEurojackpotExtraNumbersValueIsInvalid() {
        // given
        GameType gameType = GameType.EJP;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,1000))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    // KENO
    @Test
    void shouldReturnTrueWhenGivenKenoNumbersAreValid() {
        // given
        GameType gameType = GameType.KENO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6,7,8,9,10))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnTrueWhenGivenKenoNumbersAmountAreInvalid() {
        // given
        GameType gameType = GameType.KENO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6,7,8,9,10,11))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAtLeastOneOfGivenKenoNumbersValueIsInvalid() {
        // given
        GameType gameType = GameType.KENO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6,7,8,9,1000))
                .build();

        // when
        boolean result = numbersValidatorPort.validate(numbers);

        // then
        assertThat(result).isFalse();
    }
}