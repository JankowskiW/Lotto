package pl.wj.lotto.domain.ticket.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

import java.util.List;

@RequiredArgsConstructor
public class TicketService {
    private final NotificationPort notificationPort;
    private final TicketRepositoryPort ticketRepositoryPort;

    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = TicketMapper.toTicket(ticketRequestDto);
        if (ticket.getUserId() != null && !ticket.getUserId().isBlank()) {
            notificationPort.send("email@email.com", "Message");
        } else {
            ticket.setUserId("");
        }
        return TicketMapper.toTicketResponseDto(ticketRepositoryPort.save(ticket));
    }

    public List<TicketResponseDto> getTicketsByUserId(String userId) {
        List<Ticket> tickets = ticketRepositoryPort.getByUserId(userId);
        // TODO: Create method which gets next drawing time for specific drawing type somewhere and call it here
        return TicketMapper.toTicketResponseDtos(tickets);
    }

}
