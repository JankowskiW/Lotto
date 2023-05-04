package pl.wj.lotto.domain.draw.model.dto;

import lombok.Builder;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.Numbers;

import java.time.LocalDateTime;

@Builder
public record DrawResultDto (
    GameType type,
    Numbers numbers,
    LocalDateTime drawDateTime
) {}
