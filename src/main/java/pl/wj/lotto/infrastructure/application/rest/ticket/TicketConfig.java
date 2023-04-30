package pl.wj.lotto.infrastructure.application.rest.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(TicketRepositoryPort ticketRepositoryPort,
                                               NotificationPort emailNotificationPort,
                                               DrawDateTimeCheckerPort drawDateTimeCheckerPort,
                                               NumbersGeneratorPort numbersGeneratorPort,
                                               NumbersValidatorPort numbersValidatorPort) {
        TicketService ticketService = new TicketService(ticketRepositoryPort, emailNotificationPort,
                drawDateTimeCheckerPort, numbersGeneratorPort, numbersValidatorPort);
        return new TicketServiceAdapter(ticketService);
    }
}
