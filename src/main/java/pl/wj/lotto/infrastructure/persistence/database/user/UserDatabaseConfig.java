package pl.wj.lotto.infrastructure.persistence.database.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.user.repository.UserRepository;

@Configuration
@Profile("!fake")
public class UserDatabaseConfig {

    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepository userRepository) {
        return new UserDatabaseAdapter(userRepository);
    }
}
