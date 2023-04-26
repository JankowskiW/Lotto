package pl.wj.lotto.domain.ticket.port.out;

import pl.wj.lotto.domain.ticket.model.Ticket;

import java.util.List;

public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);

    List<Ticket> getByUserId(String userId);
}
