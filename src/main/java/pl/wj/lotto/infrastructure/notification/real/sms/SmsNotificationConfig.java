package pl.wj.lotto.infrastructure.notification.real.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.notification.NotificationPort;

@Configuration
@Profile("!fake")
public class SmsNotificationConfig {
    @Bean
    public NotificationPort smsNotificationPort() {
        return new SmsNotificationAdapter();
    }
}
