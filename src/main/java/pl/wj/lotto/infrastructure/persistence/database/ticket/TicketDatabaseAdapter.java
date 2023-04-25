package pl.wj.lotto.infrastructure.persistence.database.ticket;

import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

public class TicketDatabaseAdapter implements TicketRepositoryPort {
    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }
}
