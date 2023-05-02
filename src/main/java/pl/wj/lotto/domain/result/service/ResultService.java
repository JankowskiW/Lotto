package pl.wj.lotto.domain.result.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.result.helper.resultchecker.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.SummarizedResultsResponseDto;
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

    public List<SummarizedResultsResponseDto> getDrawSummarizedResults(String drawId) {
        DrawResultDto drawResultDto = resultCheckerPort.getResultByDrawId(drawId);
//        LocalDateTime drawDateTime = drawServicePort.getDrawDateTimeByDrawId(drawId);
        List<PlayerNumbersDto> playersNumbers = ticketServicePort.getPlayersNumbersForDraw(drawId);
        return List.of();
    }
}
