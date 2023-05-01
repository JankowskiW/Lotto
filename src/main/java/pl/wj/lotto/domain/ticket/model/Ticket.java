package pl.wj.lotto.domain.ticket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.Numbers;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Ticket {
    String id;
    String userId;
    GameType gameType;
    int numberOfDraws;
    Numbers numbers;
    LocalDateTime generationDateTime;
}
