package pl.wj.lotto.domain.ticket.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.service.TicketService;

@RequiredArgsConstructor
public class TicketServiceAdapter implements TicketServicePort {
    private final TicketService ticketService;


    @Override
    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        return ticketService.addTicket(ticketRequestDto);
    }
}
