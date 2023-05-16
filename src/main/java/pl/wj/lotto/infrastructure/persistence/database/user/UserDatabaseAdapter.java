package pl.wj.lotto.infrastructure.persistence.database.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wj.lotto.domain.user.mapper.UserMapper;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;
import pl.wj.lotto.infrastructure.persistence.database.user.repository.UserRepository;

@RequiredArgsConstructor
public class UserDatabaseAdapter implements UserRepositoryPort {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = UserMapper.toUserEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toUser(userEntity);
    }

    @Override
    public UserDetails findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
