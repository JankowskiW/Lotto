package pl.wj.lotto.domain.common.numbers;

import lombok.Builder;
import pl.wj.lotto.domain.common.drawtime.model.DrawTime;
import pl.wj.lotto.domain.common.gametype.GameType;

import java.util.List;

@Builder
public record Numbers (
        GameType gameType,
        DrawTime drawTime,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers) {
}
