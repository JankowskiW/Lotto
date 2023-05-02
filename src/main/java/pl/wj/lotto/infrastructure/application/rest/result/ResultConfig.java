package pl.wj.lotto.infrastructure.application.rest.result;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.result.adapter.ResultServiceAdapter;
import pl.wj.lotto.domain.result.helper.resultchecker.ResultChecker;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;
import pl.wj.lotto.domain.result.service.ResultService;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;

@Configuration
public class ResultConfig {

    @Bean
    public ResultCheckerPort resultCheckerPort() {
        return new ResultChecker();
    }

    @Bean
    public ResultServicePort resultServicePort(ResultCheckerPort resultCheckerPort, TicketServicePort ticketServicePort,
                                               DrawServicePort drawServicePort) {
        ResultService resultService = new ResultService(resultCheckerPort, ticketServicePort, drawServicePort);
        return new ResultServiceAdapter(resultService);
    }
}
