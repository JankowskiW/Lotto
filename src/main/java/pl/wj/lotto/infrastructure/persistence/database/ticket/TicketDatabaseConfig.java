package pl.wj.lotto.infrastructure.persistence.database.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.ticket.repository.TicketRepository;

@Configuration
@Profile("!fake")
public class TicketDatabaseConfig {
    @Bean
    public TicketRepositoryPort ticketRepositoryPort(TicketRepository ticketRepository) {
        return new TicketDatabaseAdapter(ticketRepository);
    }
}
