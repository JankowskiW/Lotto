package pl.wj.lotto.infrastructure.persistence.fake.ticket;

import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketFakeAdapter implements TicketRepositoryPort {
    private final Map<String, Ticket> ticketsTable = new ConcurrentHashMap<>();
    @Override
    public Ticket save(Ticket ticket) {
        String id = ticket.getId() == null ? UUID.randomUUID().toString() : ticket.getId();
        ticket.setId(id);
        ticket.setGenerationDateTime(LocalDateTime.now());
        ticketsTable.put(id, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getByUserId(String userId) {
        return ticketsTable.values().stream().filter(t -> t.getUserId().equals(userId)).toList();
    }
}
