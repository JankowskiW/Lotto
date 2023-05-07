package pl.wj.lotto.domain.result.helper.resultchecker.port.in;

import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.util.List;
import java.util.Map;

public interface ResultCheckerPort {
    Map<String, Integer> getResultsForDraw(DrawResultDto drawResultDto, List<PlayerNumbersDto> playerNumbersDtos);

    TicketResultsDetailsDto getResultsForTicket(Ticket ticket, List<Draw> ticketDraws);
}
