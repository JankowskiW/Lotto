package pl.wj.lotto.domain.ticket.model.dto;

import pl.wj.lotto.domain.common.gametype.GameType;

import java.util.List;

public record PlayerNumbersDto (
    String userId,
    String drawId,
    GameType gameType,
    List<Integer> mainNumbers,
    List<Integer> extraNumbers
) {}
