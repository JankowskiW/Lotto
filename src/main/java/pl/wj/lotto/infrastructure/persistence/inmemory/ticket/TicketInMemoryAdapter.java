package pl.wj.lotto.infrastructure.persistence.inmemory.ticket;

import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketInMemoryAdapter implements TicketRepositoryPort {
    private final Map<String, Ticket> ticketsTable = new ConcurrentHashMap<>();
    @Override
    public Ticket save(Ticket ticket) {
        String id = ticket.getId() == null ? UUID.randomUUID().toString() : ticket.getId();
        ticket.setId(id);
        ticketsTable.put(id, ticket);
        return ticket;
    }
}