package pl.wj.lotto.domain.result.port.in;

import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;

public interface ResultServicePort {
    TicketResultsDetailsDto getTicketResults(String ticketId);

    DrawResultDetailsResponseDto getDrawResultDetails(String drawId);
}
