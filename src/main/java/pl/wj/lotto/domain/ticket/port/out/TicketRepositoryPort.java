package pl.wj.lotto.domain.ticket.port.out;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);

    List<Ticket> getByUserId(String userId);

    List<TicketEntity> getPlayersDrawNumbersByGameTypeAndLastDrawDateTime(GameType type, LocalDateTime drawDateTime);

    Optional<TicketEntity> findById(String ticketId);
}
