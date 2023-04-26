package pl.wj.lotto.infrastructure.persistence.database.ticket;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;
import pl.wj.lotto.infrastructure.persistence.database.ticket.repository.TicketRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TicketDatabaseAdapter implements TicketRepositoryPort {
    private final TicketRepository ticketRepository;
    @Override
    public Ticket save(Ticket ticket) {
        ticket.setGenerationTime(LocalDateTime.now());
        TicketEntity ticketEntity = TicketMapper.toTicketEntity(ticket);
        ticketEntity = ticketRepository.save(ticketEntity);
        return TicketMapper.toTicket(ticketEntity);
    }
}
