package pl.wj.lotto.domain.common.numbers;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.infrastructure.gametype.properties.settings.GameTypeSettingsProperties;

import java.util.List;

@RequiredArgsConstructor
public class NumbersValidator implements NumbersValidatorPort {
    private final GameTypeSettingsContainer gameTypeSettingsContainer;

    public boolean validate(Numbers numbers) {
        if (numbers.mainNumbers().stream().distinct().count() != (long) numbers.mainNumbers().size() ||
                (numbers.extraNumbers() != null && numbers.extraNumbers().stream().distinct().count() != (long) numbers.extraNumbers().size()))
            return false;
        return switch(numbers.gameType()) {
            case LOTTO -> validateLotto(numbers.mainNumbers());
            case Q600 -> validateQuick600(numbers.mainNumbers());
            case EJP -> validateEurojackpot(numbers.mainNumbers(), numbers.extraNumbers());
            case KENO -> validateKeno(numbers.mainNumbers());
        };
    }

    private boolean validateLotto(List<Integer> mainNumbers) {
        GameTypeSettingsProperties requirements = gameTypeSettingsContainer.settings().get(GameType.LOTTO);
        return mainNumbers.size() == requirements.getMainNumbersAmount() &&
                mainNumbers.stream().noneMatch(n -> (n < requirements.getMainNumbersMinValue() || n > requirements.getMainNumbersMaxValue()));
    }

    private boolean validateQuick600(List<Integer> mainNumbers) {
        GameTypeSettingsProperties requirements = gameTypeSettingsContainer.settings().get(GameType.Q600);
        return mainNumbers.size() == requirements.getMainNumbersAmount() &&
                mainNumbers.stream().noneMatch(n -> (n < requirements.getMainNumbersMinValue() || n > requirements.getMainNumbersMaxValue()));
    }

    private boolean validateEurojackpot(List<Integer> mainNumbers, List<Integer> extraNumbers) {
        GameTypeSettingsProperties requirements = gameTypeSettingsContainer.settings().get(GameType.EJP);
        return mainNumbers.size() == requirements.getMainNumbersAmount() &&
                extraNumbers.size() == requirements.getExtraNumbersAmount() &&
                mainNumbers.stream().noneMatch(n -> (n < requirements.getMainNumbersMinValue() || n > requirements.getMainNumbersMaxValue())) &&
                extraNumbers.stream().noneMatch(n -> (n < requirements.getExtraNumbersMinValue() || n > requirements.getExtraNumbersMaxValue()));
    }

    private boolean validateKeno(List<Integer> mainNumbers) {
        GameTypeSettingsProperties requirements = gameTypeSettingsContainer.settings().get(GameType.KENO);
        return mainNumbers.size() >= requirements.getMainTicketNumbersMinAmount() && mainNumbers.size() <= requirements.getMainTicketNumbersMaxAmount() &&
                mainNumbers.stream().noneMatch(n -> (n < requirements.getMainNumbersMinValue() || n > requirements.getMainNumbersMaxValue()));
    }


}
