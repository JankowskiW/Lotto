package pl.wj.lotto.infrastructure.notification.fake.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.notification.NotificationPort;

@Configuration
@Profile("fake")
public class SMSNotificationFakeConfig {
    @Bean
    public NotificationPort smsNotificationPort() {
        return new SMSNotificationFakeAdapter();
    }
}
