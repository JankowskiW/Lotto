package pl.wj.lotto.infrastructure.notification.fake.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.notification.NotificationPort;

@Configuration
@Profile("fake")
public class EmailNotificationFakeConfig {
    @Bean
    public NotificationPort emailNotificationPort() {
        return new EmailNotificationFakeAdapter();
    }
}
