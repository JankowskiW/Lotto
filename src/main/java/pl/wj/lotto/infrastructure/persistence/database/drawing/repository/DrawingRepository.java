package pl.wj.lotto.infrastructure.persistence.database.drawing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.infrastructure.persistence.database.drawing.entity.DrawingEntity;

import java.util.List;

@Repository
public interface DrawingRepository extends MongoRepository<DrawingEntity, String> {
    List<DrawingEntity> findAllByType(DrawingType drawingType);
}
