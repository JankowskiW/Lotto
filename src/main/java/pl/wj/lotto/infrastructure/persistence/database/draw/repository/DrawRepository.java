package pl.wj.lotto.infrastructure.persistence.database.draw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.vo.DrawGameTypeAndDateTimeVo;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrawRepository extends MongoRepository<DrawEntity, String> {
    List<DrawEntity> findAllByType(GameType gameType);


    // TODO: check how to put data to dto/vo object using MongoRepository
    @Query("SELECT new pl.wj.lotto.domain.draw.model.vo.DrawGameTypeAndDateTimeVo(d.gameType, d.drawDateTime) " +
            "FROM draws d WHERE d.id = :id")
    Optional<DrawGameTypeAndDateTimeVo> findDrawGameTypeAndDateTimeById(String id);
}
