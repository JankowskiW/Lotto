package pl.wj.lotto.infrastructure.notification.real.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.common.notification.NotificationPort;

@Configuration
@Profile("!in-memory")
public class EmailNotificationSmtpConfig {
    @Bean
    public NotificationPort emailNotificationPort() {
        return new EmailNotificationSmtpAdapter();
    }
}
