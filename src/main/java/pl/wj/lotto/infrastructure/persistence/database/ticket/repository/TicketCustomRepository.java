package pl.wj.lotto.infrastructure.persistence.database.ticket.repository;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketCustomRepository {
    List<TicketEntity> getPlayersDrawNumbersByGameTypeAndLastDrawDateTime(GameType type, LocalDateTime drawDateTime);
}
