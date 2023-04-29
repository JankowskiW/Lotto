package pl.wj.lotto.infrastructure.persistence.database.draw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;

import java.util.List;

@Repository
public interface DrawRepository extends MongoRepository<DrawEntity, String> {
    List<DrawEntity> findAllByType(GameType gameType);
}
