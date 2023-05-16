package pl.wj.lotto.domain.user.port.out;

import org.springframework.security.core.userdetails.UserDetails;
import pl.wj.lotto.domain.user.model.User;

public interface UserRepositoryPort {
    User save(User user);

    UserDetails findByUsername(String username);
}
