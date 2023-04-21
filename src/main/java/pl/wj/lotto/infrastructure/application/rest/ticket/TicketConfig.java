package pl.wj.lotto.infrastructure.application.rest.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.notification.EmailNotificationPort;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;
import pl.wj.lotto.infrastructure.notification.inmemory.email.EmailNotificationAdapter;
import pl.wj.lotto.infrastructure.persistence.inmemory.ticket.TicketInMemoryAdapter;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(TicketRepositoryPort ticketRepositoryPort,
                                               NotificationPort notificationPort) {
        TicketService ticketService = new TicketService(notificationPort, ticketRepositoryPort);
        return new TicketServiceAdapter(ticketService);
    }

    @Bean
    public TicketRepositoryPort ticketRepositoryPort() {
        return new TicketInMemoryAdapter();
    }

    @Bean
    public NotificationPort notificationPort() {
        return new EmailNotificationAdapter();
    }
}
