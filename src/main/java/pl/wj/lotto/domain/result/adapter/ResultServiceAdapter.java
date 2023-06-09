package pl.wj.lotto.domain.result.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;
import pl.wj.lotto.domain.result.service.ResultService;

@RequiredArgsConstructor
public class ResultServiceAdapter implements ResultServicePort {
    private final ResultService resultService;

    @Override
    public TicketResultsDetailsDto getTicketResultsDetails(String ticketId) {
        return resultService.getTicketResultsDetails(ticketId);
    }

    @Override
    @Cacheable("drawsResults")
    public DrawResultDetailsResponseDto getDrawResultDetails(String drawId) {
        return resultService.getDrawResultDetails(drawId);
    }
}
