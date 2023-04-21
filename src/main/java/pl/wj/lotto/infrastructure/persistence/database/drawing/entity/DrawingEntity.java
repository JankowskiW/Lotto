package pl.wj.lotto.infrastructure.persistence.database.drawing.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Document(collection = "drawings")
public class DrawingEntity {
    @Id
    String id;
    int typeId;
    List<Integer> mainNumbers;
    List<Integer> extraNumbers;
    LocalDateTime drawingTime;
}