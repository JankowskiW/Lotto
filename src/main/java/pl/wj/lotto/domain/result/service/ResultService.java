package pl.wj.lotto.domain.result.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ResultService {
    private final ResultCheckerPort resultCheckerPort;
    private final TicketServicePort ticketServicePort;
    private final DrawServicePort drawServicePort;


    public TicketResultsDetailsDto getTicketResults(String ticketId) {
//        // TODO: Make sure that draws for ticket should be retrieved in drawServicePort
//        TicketResponseDto ticket = ticketServicePort.getTicket(ticketId);
//        List<DrawResponseDto> ticketDraws = drawServicePort.getDrawsForTicket(ticketId);
//        return resultCheckerPort.getResultsForTicket(ticket, ticketDraws);
        return TicketResultsDetailsDto.builder().build();
    }

    public DrawResultDetailsResponseDto getDrawResultDetails(String drawId) {
        DrawResultDto drawResultDto = drawServicePort.getDrawResult(drawId);
        List<PlayerNumbersDto> playerNumbersDtos = ticketServicePort.getPlayersDrawNumbers(drawResultDto);
        Map<String, Integer> results = resultCheckerPort.getResultsForDraw(drawResultDto, playerNumbersDtos);
        return DrawResultDetailsResponseDto.builder()
                .drawId(drawId)
                .drawDateTime(drawResultDto.drawDateTime())
                .results(results)
                .build();
    }
}
