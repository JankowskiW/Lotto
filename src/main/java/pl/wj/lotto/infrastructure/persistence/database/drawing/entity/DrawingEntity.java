package pl.wj.lotto.infrastructure.persistence.database.drawing.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wj.lotto.domain.common.numberstemplate.model.NumbersTemplate;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "drawings")
public class DrawingEntity {
    @Id
    String id;
    int typeId;
    NumbersTemplate numbers;
    LocalDateTime drawingTime;
}