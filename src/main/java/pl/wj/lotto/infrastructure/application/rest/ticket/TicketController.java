package pl.wj.lotto.infrastructure.application.rest.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketServicePort ticketServicePort;

    @PostMapping
    public TicketResponseDto addTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        return ticketServicePort.addTicket(ticketRequestDto);
    }

    @GetMapping("/users/{id}")
    public List<TicketResponseDto> getUserTickets(@PathVariable String id) {
        return ticketServicePort.getUserTickets(id);
    }
}
