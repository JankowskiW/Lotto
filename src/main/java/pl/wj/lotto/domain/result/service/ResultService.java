package pl.wj.lotto.domain.result.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.result.helper.resultchecker.model.dto.ResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsResponseDto;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;

import java.util.List;

@RequiredArgsConstructor
public class ResultService {
    private final ResultCheckerPort resultCheckerPort;
    private final TicketServicePort ticketServicePort;
    private final DrawServicePort drawServicePort;


    public List<TicketResultsResponseDto> getTicketResults(String ticketId) {
        return List.of();
    }

    public List<DrawResultDetailsResponseDto> getDrawResultDetails(String drawId) {
        DrawResultDto drawResultDto = drawServicePort.getDrawResult(drawId);
        List<PlayerNumbersDto> playerNumbersDtos = ticketServicePort.getPlayersDrawNumbers(drawResultDto);
        List<ResultDto> resultDtos = resultCheckerPort.getResultsForDraw(drawResultDto, playerNumbersDtos);



        return List.of();
    }
}
