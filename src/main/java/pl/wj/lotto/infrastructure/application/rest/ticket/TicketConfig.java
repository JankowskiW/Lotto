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
import pl.wj.lotto.domain.user.port.in.UserServicePort;

import java.time.Clock;

@Configuration
public class TicketConfig {
    @Bean
    public TicketServicePort ticketServicePort(Clock clock,
                                               TicketRepositoryPort ticketRepositoryPort,
                                               UserServicePort userServicePort,
                                               DrawDateTimeCheckerPort drawDateTimeCheckerPort,
                                               NumbersGeneratorPort numbersGeneratorPort,
                                               NumbersValidatorPort numbersValidatorPort,
                                               NotificationPort emailNotificationPort,
                                               NotificationPort smsNotificationPort) {
        TicketService ticketService = new TicketService(
                clock, ticketRepositoryPort, userServicePort,
                drawDateTimeCheckerPort, numbersGeneratorPort, numbersValidatorPort,
                emailNotificationPort, smsNotificationPort);
        return new TicketServiceAdapter(ticketService);
    }
}
