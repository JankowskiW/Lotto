package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;
import lombok.With;
import pl.wj.lotto.domain.common.gametype.GameType;

import java.util.List;

@Builder
@With
public record PlayerNumbersDto (
    String userId,
    String drawId,
    GameType gameType,
    List<Integer> mainNumbers,
    List<Integer> extraNumbers
) {}
