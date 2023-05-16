package pl.wj.lotto.infrastructure.persistence.database.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserDetails findByUsername(String username);
}
