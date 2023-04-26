package pl.wj.lotto.infrastructure.application.rest.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketServicePort ticketServicePort;

    @PostMapping
    public TicketResponseDto addTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        return ticketServicePort.addTicket(ticketRequestDto);
    }
}
