package pl.wj.lotto.domain.result.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;
import pl.wj.lotto.domain.result.service.ResultService;

@RequiredArgsConstructor
public class ResultServiceAdapter implements ResultServicePort {
    private final ResultService resultService;

    @Override
    public TicketResultsDetailsDto getTicketResults(String ticketId) {
        return resultService.getTicketResults(ticketId);
    }

    @Override
    public DrawResultDetailsResponseDto getDrawResultDetails(String drawId) {
        return resultService.getDrawResultDetails(drawId);
    }
}
