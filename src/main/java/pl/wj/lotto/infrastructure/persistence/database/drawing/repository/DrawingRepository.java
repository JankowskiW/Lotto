package pl.wj.lotto.infrastructure.persistence.database.drawing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wj.lotto.infrastructure.persistence.database.drawing.entity.DrawingEntity;

import java.util.List;

public interface DrawingRepository extends MongoRepository<DrawingEntity, String> {

    List<DrawingEntity> findAllByTypeId(int typeId);
}
