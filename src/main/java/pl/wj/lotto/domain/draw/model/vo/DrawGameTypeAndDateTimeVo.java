package pl.wj.lotto.domain.draw.model.vo;

import pl.wj.lotto.domain.common.gametype.GameType;

import java.time.LocalDateTime;

public record DrawGameTypeAndDateTimeVo(
        GameType gameType,
        LocalDateTime drawDateTime
) {
}
