package pl.wj.lotto.infrastructure.application.rest.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wj.lotto.domain.user.adapter.UserServiceAdapter;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;
import pl.wj.lotto.domain.user.service.UserService;

@Configuration
public class UserConfig {
    @Bean
    public UserServicePort userServicePort(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        UserService userService = new UserService(userRepositoryPort, passwordEncoder);
        return new UserServiceAdapter(userService);
    }
}
