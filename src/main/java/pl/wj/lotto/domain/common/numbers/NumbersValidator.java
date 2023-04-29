package pl.wj.lotto.domain.common.numbers;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettings;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;

import java.util.List;

public class NumbersValidator {

    public boolean validate(GameType gameType, List<Integer> mainNumbers, List<Integer> extraNumbers) {
        return switch(gameType) {
            case LOTTO -> validateLotto(mainNumbers);
            case Q600 -> validateQuick600(mainNumbers);
            case EJP -> validateEurojackpot(mainNumbers, extraNumbers);
            case KENO -> validateKeno(mainNumbers);
        };
    }

    private boolean validateLotto(List<Integer> mainNumbers) {
        GameTypeSettings requirements = GameTypeSettingsContainer.getGameTypeSettings(GameType.LOTTO);
        if (mainNumbers.size() != requirements.minAmountOfMainNumbers())
            return false;
        if (mainNumbers.stream().anyMatch(n -> (n < requirements.minValueOfMainNumbers() || n > requirements.maxValueOfMainNumbers())))
            return false;
        return true;
    }

    private boolean validateQuick600(List<Integer> mainNumbers) {
        GameTypeSettings requirements = GameTypeSettingsContainer.getGameTypeSettings(GameType.Q600);
        if (mainNumbers.size() != requirements.minAmountOfMainNumbers())
            return false;
        if (mainNumbers.stream().anyMatch(n -> (n < requirements.minValueOfMainNumbers() || n > requirements.maxValueOfMainNumbers())))
            return false;
        return true;
    }

    private boolean validateEurojackpot(List<Integer> mainNumbers, List<Integer> extraNumbers) {
        GameTypeSettings requirements = GameTypeSettingsContainer.getGameTypeSettings(GameType.EJP);
        if (mainNumbers.size() != requirements.minAmountOfMainNumbers())
            return false;
        if (mainNumbers.stream().anyMatch(n -> (n < requirements.minValueOfMainNumbers() || n > requirements.maxValueOfMainNumbers())))
            return false;
        if (extraNumbers.size() != requirements.minAmountOfExtraNumbers())
            return false;
        if (extraNumbers.stream().anyMatch(n -> (n < requirements.minValueOfExtraNumbers() || n > requirements.maxValueOfExtraNumbers())))
            return false;
        return true;
    }

    private boolean validateKeno(List<Integer> mainNumbers) {
        GameTypeSettings requirements = GameTypeSettingsContainer.getGameTypeSettings(GameType.KENO);
        if (mainNumbers.size() < requirements.minAmountOfMainNumbers() || mainNumbers.size() > requirements.maxAmountOfMainNumbers())
            return false;
        if (mainNumbers.stream().anyMatch(n -> (n < requirements.minValueOfMainNumbers() || n > requirements.maxValueOfMainNumbers())))
            return false;
        return true;
    }


}
