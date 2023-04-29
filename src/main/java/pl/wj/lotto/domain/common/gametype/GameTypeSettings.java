package pl.wj.lotto.domain.common.gametype;

import lombok.Builder;
import pl.wj.lotto.domain.common.drawtime.model.DrawTime;

@Builder
public record GameTypeSettings(
        GameType gameType,
        DrawTime drawTime,
        int minAmountOfMainNumbers,
        int maxAmountOfMainNumbers,
        int minValueOfMainNumbers,
        int maxValueOfMainNumbers,
        int minAmountOfExtraNumbers,
        int maxAmountOfExtraNumbers,
        int minValueOfExtraNumbers,
        int maxValueOfExtraNumbers
)
{
}
