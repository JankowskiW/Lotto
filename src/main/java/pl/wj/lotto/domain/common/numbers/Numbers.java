package pl.wj.lotto.domain.common.numbers;

import lombok.Builder;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;
import pl.wj.lotto.domain.common.gametype.GameType;

import java.util.List;

@Builder
public record Numbers (
        GameType gameType,
        DrawDateTimeSettings drawDateTimeSettings,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers) {
}
