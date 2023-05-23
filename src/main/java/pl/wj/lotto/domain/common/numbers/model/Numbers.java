package pl.wj.lotto.domain.common.numbers.model;

import lombok.Builder;
import pl.wj.lotto.domain.common.gametype.GameType;

import java.util.List;

@Builder
public record Numbers (
        GameType gameType,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers
) {}
