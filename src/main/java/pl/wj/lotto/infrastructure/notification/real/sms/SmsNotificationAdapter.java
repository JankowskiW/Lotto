package pl.wj.lotto.infrastructure.notification.real.sms;

import lombok.extern.log4j.Log4j2;
import pl.wj.lotto.domain.common.notification.NotificationPort;

import java.util.List;

@Log4j2
public class SmsNotificationAdapter implements NotificationPort {
    @Override
    public void send(List<String> recipients, String message) {
        log.error("SMS configuration not found");
    }

    @Override
    public void send(String recipient, String message) {
        log.error("SMS configuration not found");
    }
}
