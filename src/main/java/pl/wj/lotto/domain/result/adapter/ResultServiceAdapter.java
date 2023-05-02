package pl.wj.lotto.domain.result.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.result.model.dto.SummarizedResultsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsResponseDto;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;
import pl.wj.lotto.domain.result.service.ResultService;

import java.util.List;

@RequiredArgsConstructor
public class ResultServiceAdapter implements ResultServicePort {
    private final ResultService resultService;

    @Override
    public List<TicketResultsResponseDto> getTicketResults(String ticketId) {
        return resultService.getTicketResults(ticketId);
    }

    @Override
    public List<SummarizedResultsResponseDto> getDrawSummarizedResults(String drawId) {
        return resultService.getDrawSummarizedResults(drawId);
    }
}
