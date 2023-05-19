package pl.wj.lotto.infrastructure.application.rest.ticket;

import jakarta.validation.Valid;
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
    public TicketResponseDto addTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        return ticketServicePort.addTicket(ticketRequestDto);
    }

    @GetMapping("/users/{userId}")
    public List<TicketResponseDto> getUserTickets(@PathVariable String userId) {
        return ticketServicePort.getUserTickets(userId);
    }
}
