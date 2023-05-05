package pl.wj.lotto.domain.result.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
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
    public DrawResultDetailsResponseDto getDrawResultDetails(String drawId) {
        return resultService.getDrawResultDetails(drawId);
    }
}
