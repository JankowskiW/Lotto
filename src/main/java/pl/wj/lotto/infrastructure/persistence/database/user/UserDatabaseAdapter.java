package pl.wj.lotto.infrastructure.persistence.database.user;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.user.mapper.UserMapper;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;
import pl.wj.lotto.infrastructure.persistence.database.user.repository.UserRepository;

import java.util.Optional;

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
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.map(UserMapper::toUser);
    }
}
