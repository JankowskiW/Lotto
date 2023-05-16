package pl.wj.lotto.infrastructure.persistence.database.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}
