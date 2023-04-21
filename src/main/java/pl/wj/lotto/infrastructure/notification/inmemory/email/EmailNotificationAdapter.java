package pl.wj.lotto.infrastructure.notification.inmemory.email;

import lombok.extern.log4j.Log4j2;
import pl.wj.lotto.domain.common.notification.EmailNotificationPort;

import java.util.List;

@Log4j2
public class EmailNotificationAdapter implements EmailNotificationPort {
    @Override
    public void send(List<String> recipients, String message) {
        recipients.forEach(r -> log.info(String.format("Email sent to: %s, with message: %s", r, message)));
    }

    @Override
    public void send(String recipient, String message) {
        log.info(String.format("Email sent to: %s, with message: %s", recipient, message));
    }
}
