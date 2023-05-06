package pl.wj.lotto.infrastructure.application.rest.result;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultServicePort resultServicePort;

    @GetMapping("/tickets/{id}")
    public TicketResultsDetailsDto getTicketResults(@PathVariable String id) {
        return resultServicePort.getTicketResults(id);
    }

    @GetMapping("/draws/{id}")
    public DrawResultDetailsResponseDto getDrawResultDetails(@PathVariable String id) {
        return resultServicePort.getDrawResultDetails(id);
    }
}
