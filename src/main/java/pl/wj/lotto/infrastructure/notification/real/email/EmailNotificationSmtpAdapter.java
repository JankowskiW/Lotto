package pl.wj.lotto.infrastructure.notification.real.email;

import lombok.extern.log4j.Log4j2;
import pl.wj.lotto.domain.common.notification.EmailNotificationPort;

import java.util.List;

@Log4j2
public class EmailNotificationSmtpAdapter implements EmailNotificationPort {
    @Override
    public void send(List<String> recipients, String message) {
        log.error("SMTP configuration not found");
    }

    @Override
    public void send(String recipient, String message) {
        log.error("SMTP configuration not found");
    }
}
