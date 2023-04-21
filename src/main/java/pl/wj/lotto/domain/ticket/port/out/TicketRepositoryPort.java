package pl.wj.lotto.domain.ticket.port.out;

import pl.wj.lotto.domain.ticket.model.Ticket;

public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);
}
