package pl.wj.lotto.infrastructure.persistence.fake.ticket;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketFakeAdapter implements TicketRepositoryPort {
    private final Map<String, Ticket> ticketsTable = new ConcurrentHashMap<>();
    @Override
    public Ticket save(Ticket ticket) {
        String id = ticket.getId() == null ? UUID.randomUUID().toString() : ticket.getId();
        ticket.setId(id);
        ticketsTable.put(id, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getByUserId(String userId) {
        return ticketsTable.values().stream().filter(t -> t.getUserId().equals(userId)).toList();
    }

    @Override
    public List<TicketEntity> getPlayersDrawNumbersByGameTypeAndLastDrawDateTime(GameType type, LocalDateTime drawDateTime) {
        return TicketMapper.toTicketEntities(ticketsTable.values()
                .stream()
                .filter(t -> t.getGameType().equals(type) && !t.getLastDrawDateTime().isBefore(drawDateTime))
                .toList());
    }

    @Override
    public Optional<TicketEntity> findById(String ticketId) {
        return Optional.of(TicketMapper.toTicketEntity(ticketsTable.get(ticketId)));
    }
}
