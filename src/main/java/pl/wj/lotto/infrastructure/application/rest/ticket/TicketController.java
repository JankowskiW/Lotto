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

    @GetMapping("/users/{userId}")
    public List<TicketResponseDto> getTicketsByUserId(@PathVariable String userId) {
        return ticketServicePort.getTicketsByUserId(userId);
    }
}
