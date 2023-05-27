package pl.wj.lotto.infrastructure.persistence.fake.user;

import pl.wj.lotto.domain.user.mapper.UserMapper;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserFakeAdapter implements UserRepositoryPort {
    private final Map<String, User> usersTable = new ConcurrentHashMap<>();
    @Override
    public User save(User user) {
        String id = user.id() == null ? UUID.randomUUID().toString() : user.id();
        usersTable.put(id, user);
        return user.withId(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Optional<User> user = usersTable.values()
                .stream()
                .filter(v -> v.username().equals(username))
                .findFirst();
        return user.map(UserMapper::toUserEntity);
    }

    @Override
    public Optional<User> findById(String userId) {
        if (!usersTable.containsKey(userId)) return Optional.empty();
        return Optional.of(usersTable.get(userId));
    }
}
