package pl.wj.lotto.domain.ticket.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

@RequiredArgsConstructor
public class TicketService {
    private final NotificationPort notificationPort;
    private final TicketRepositoryPort ticketRepositoryPort;

    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = TicketMapper.toTicket(ticketRequestDto);
        if (ticket != null && !ticket.getUserId().isBlank()) {
            notificationPort.send("email@email.com", "Message");
        }
        return TicketMapper.toTicketResponseDto(ticketRepositoryPort.save(ticket));
    }
}
