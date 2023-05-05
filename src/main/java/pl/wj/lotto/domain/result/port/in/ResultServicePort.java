package pl.wj.lotto.domain.result.port.in;

import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsResponseDto;

import java.util.List;

public interface ResultServicePort {
    List<TicketResultsResponseDto> getTicketResults(String ticketId);

    DrawResultDetailsResponseDto getDrawResultDetails(String drawId);
}
