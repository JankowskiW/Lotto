package pl.wj.lotto.infrastructure.persistence.database.draw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrawRepository extends MongoRepository<DrawEntity, String> {
    List<DrawEntity> findAllByType(GameType gameType);

    @Query(value="{ 'id' : ?0}", fields = "{'type' :  1, 'drawDateTime' :  1, 'numbers' :  1}")
    Optional<DrawResultDto> findDrawResultById(String drawId);
}
