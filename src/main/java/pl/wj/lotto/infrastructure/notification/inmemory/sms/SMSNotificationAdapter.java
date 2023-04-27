package pl.wj.lotto.infrastructure.notification.inmemory.sms;

import lombok.extern.log4j.Log4j2;
import pl.wj.lotto.domain.common.notification.NotificationPort;

import java.util.List;

@Log4j2
public class SMSNotificationAdapter implements NotificationPort {
    @Override
    public void send(List<String> recipients, String message) {
        recipients.forEach(r -> log.info(String.format("SMS sent to: %s, with message: %s", r, message)));
    }

    @Override
    public void send(String recipient, String message) {
        log.info(String.format("SMS sent to: %s, with message: %s", recipient, message));
    }
}
