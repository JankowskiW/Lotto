package pl.wj.lotto.infrastructure.persistence.fake.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;

@Configuration
@Profile("fake")
public class UserFakeConfig {
    @Bean
    public UserRepositoryPort userRepositoryPort() {
            return new UserFakeAdapter();
    }
}
