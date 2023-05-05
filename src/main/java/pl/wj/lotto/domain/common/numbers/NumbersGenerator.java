package pl.wj.lotto.domain.common.numbers;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettings;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

import java.util.List;

@RequiredArgsConstructor
public class NumbersGenerator implements NumbersGeneratorPort {
    private final NumbersReceiverPort numbersReceiverPort;
    public Numbers generate(GameType gameType, boolean sorted) {
        GameTypeSettings settings = GameTypeSettingsContainer.getGameTypeSettings(gameType);
        List<Integer> mainNumbers = numbersReceiverPort.receive(
                settings.minValueOfMainNumbers(), settings.maxValueOfMainNumbers(), settings.minAmountOfMainNumbers());
        List<Integer> extraNumbers = null;
        if (gameType == GameType.EJP) {
            extraNumbers = numbersReceiverPort.receive(
                    settings.minValueOfExtraNumbers(), settings.maxValueOfExtraNumbers(), settings.minAmountOfExtraNumbers());
        }
        if (sorted) {
            mainNumbers = mainNumbers.stream().sorted().toList();
            extraNumbers = extraNumbers != null ? extraNumbers.stream().sorted().toList() : null;
        }
        return Numbers.builder()
                .gameType(gameType)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(mainNumbers)
                .extraNumbers(extraNumbers)
                .build();
    }
}
