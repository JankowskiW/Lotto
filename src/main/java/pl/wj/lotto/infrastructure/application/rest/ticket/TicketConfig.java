package pl.wj.lotto.infrastructure.application.rest.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;
import pl.wj.lotto.infrastructure.persistence.inmemory.ticket.TicketInMemoryAdapter;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(TicketRepositoryPort ticketRepositoryPort) {
        TicketService ticketService = new TicketService(null, ticketRepositoryPort);
        return new TicketServiceAdapter(ticketService);
    }

    @Bean
    public TicketRepositoryPort ticketRepositoryPort() {
        return new TicketInMemoryAdapter();
    }
}
