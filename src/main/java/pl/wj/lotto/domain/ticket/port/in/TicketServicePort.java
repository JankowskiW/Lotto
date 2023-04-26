package pl.wj.lotto.domain.ticket.port.in;

import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;

import java.util.List;

public interface TicketServicePort {
    TicketResponseDto addTicket(TicketRequestDto ticketRequestDto);

    List<TicketResponseDto> getTicketsByUserId(String userId);
}
