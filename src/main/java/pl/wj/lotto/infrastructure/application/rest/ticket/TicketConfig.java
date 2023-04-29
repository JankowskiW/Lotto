package pl.wj.lotto.infrastructure.application.rest.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbersgenerator.NumbersGeneratorPort;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(TicketRepositoryPort ticketRepositoryPort,
                                               NotificationPort emailNotificationPort,
                                               NumbersGeneratorPort numbersGeneratorPort,
                                               DrawServicePort drawServicePort) {
        TicketService ticketService = new TicketService(
                ticketRepositoryPort, emailNotificationPort, numbersGeneratorPort, drawServicePort);
        return new TicketServiceAdapter(ticketService);
    }
}
