package pl.wj.lotto.domain.common.gametype;

import lombok.Builder;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;

@Builder
public record GameTypeSettings(
        GameType gameType,
        DrawDateTimeSettings drawDateTimeSettings,
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
