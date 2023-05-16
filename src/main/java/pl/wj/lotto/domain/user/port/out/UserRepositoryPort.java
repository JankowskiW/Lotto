package pl.wj.lotto.domain.user.port.out;

import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<UserEntity> findByUsername(String username);
}
