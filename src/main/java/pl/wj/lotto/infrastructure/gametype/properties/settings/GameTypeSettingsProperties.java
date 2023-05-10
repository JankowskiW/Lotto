package pl.wj.lotto.infrastructure.gametype.properties.settings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GameTypeSettingsProperties {
    int mainNumbersAmount;
    int mainTicketNumbersMinAmount;
    int mainTicketNumbersMaxAmount;
    int mainNumbersMinValue;
    int mainNumbersMaxValue;
    int extraNumbersAmount;
    int extraNumbersMinValue;
    int extraNumbersMaxValue;
}