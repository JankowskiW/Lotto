package pl.wj.lotto.infrastructure.notification.inmemory.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.notification.NotificationPort;

@Configuration
public class SMSNotificationConfig {
    @Bean
    public NotificationPort smsNotificationPort() {
        return new SMSNotificationAdapter();
    }
}
