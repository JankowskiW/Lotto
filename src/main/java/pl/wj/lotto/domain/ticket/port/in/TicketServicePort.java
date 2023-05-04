package pl.wj.lotto.domain.ticket.port.in;

import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;

import java.util.List;

public interface TicketServicePort {
    TicketResponseDto addTicket(TicketRequestDto ticketRequestDto);

    List<TicketResponseDto> getUserTickets(String userId);

    List<PlayerNumbersDto> getPlayersDrawNumbers(DrawResultDto drawResultDto);

}
