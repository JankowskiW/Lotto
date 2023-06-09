package pl.wj.lotto.domain.ticket.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.service.TicketService;

import java.util.List;

@RequiredArgsConstructor
public class TicketServiceAdapter implements TicketServicePort {
    private final TicketService ticketService;

    @Override
    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        return ticketService.addTicket(ticketRequestDto);
    }

    @Override
    public List<TicketResponseDto> getUserTickets(String userId) {
        return ticketService.getUserTickets(userId);
    }

    @Override
    public List<PlayerNumbersDto> getPlayersDrawNumbers(DrawWinningNumbersDto drawWinningNumbersDto) {
        return ticketService.getPlayersDrawNumbers(drawWinningNumbersDto);
    }

    @Override
    public Ticket getTicket(String ticketId) {
        return ticketService.getTicket(ticketId);
    }
}
