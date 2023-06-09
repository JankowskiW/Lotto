package pl.wj.lotto.infrastructure.persistence.fake.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

@Configuration
@Profile("fake")
public class TicketFakeConfig {
    @Bean
    public TicketRepositoryPort ticketRepositoryPort() {
        return new TicketFakeAdapter();
    }
}
