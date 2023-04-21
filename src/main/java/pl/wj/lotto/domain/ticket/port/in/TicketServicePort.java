package pl.wj.lotto.domain.ticket.port.in;

import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;

public interface TicketServicePort {
    TicketResponseDto addTicket(TicketRequestDto ticketRequestDto);
}
