package pl.wj.lotto.infrastructure.notification.inmemory.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.notification.NotificationPort;

@Configuration
@Profile("mem")
public class EmailNotificationConfig {
    @Bean
    public NotificationPort emailNotificationPort() {
        return new EmailNotificationInMemoryAdapter();
    }
}
