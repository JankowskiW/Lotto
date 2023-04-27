package pl.wj.lotto.infrastructure.application.rest.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbergenerator.NumberGeneratorPort;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(TicketRepositoryPort ticketRepositoryPort,
                                               NotificationPort emailNotificationPort,
                                               NumberGeneratorPort numberGeneratorPort) {
        TicketService ticketService = new TicketService(ticketRepositoryPort, emailNotificationPort, numberGeneratorPort);
        return new TicketServiceAdapter(ticketService);
    }
}
