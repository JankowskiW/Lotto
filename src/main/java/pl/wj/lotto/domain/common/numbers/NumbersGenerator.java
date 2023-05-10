package pl.wj.lotto.domain.common.numbers;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.infrastructure.gametype.properties.settings.GameTypeSettingsProperties;

import java.util.List;

@RequiredArgsConstructor
public class NumbersGenerator implements NumbersGeneratorPort {
    private final NumbersReceiverPort numbersReceiverPort;
    private final GameTypeSettingsContainer gameTypeSettingsContainer;

    public Numbers generate(GameType gameType, boolean sorted) {
        GameTypeSettingsProperties settings = gameTypeSettingsContainer.settings().get(gameType);
        List<Integer> mainNumbers = numbersReceiverPort.receive(
                settings.getMainNumbersMinValue(), settings.getMainNumbersMaxValue(), settings.getMainNumbersAmount());
        List<Integer> extraNumbers = null;
        if (gameType == GameType.EJP) {
            extraNumbers = numbersReceiverPort.receive(
                    settings.getExtraNumbersMinValue(), settings.getExtraNumbersMaxValue(), settings.getExtraNumbersAmount());
        }
        if (sorted) {
            mainNumbers = mainNumbers.stream().sorted().toList();
            extraNumbers = extraNumbers != null ? extraNumbers.stream().sorted().toList() : null;
        }
        return Numbers.builder()
                .gameType(gameType)
                .mainNumbers(mainNumbers)
                .extraNumbers(extraNumbers)
                .build();
    }
}
