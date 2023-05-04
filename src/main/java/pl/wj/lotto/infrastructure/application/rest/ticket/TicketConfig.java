package pl.wj.lotto.infrastructure.application.rest.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;

import java.time.Clock;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(Clock clock,
                                               TicketRepositoryPort ticketRepositoryPort,
                                               DrawDateTimeCheckerPort drawDateTimeCheckerPort,
                                               NumbersGeneratorPort numbersGeneratorPort,
                                               NumbersValidatorPort numbersValidatorPort) {
        TicketService ticketService = new TicketService(clock, ticketRepositoryPort, drawDateTimeCheckerPort,
                numbersGeneratorPort, numbersValidatorPort);
        return new TicketServiceAdapter(ticketService);
    }
}
