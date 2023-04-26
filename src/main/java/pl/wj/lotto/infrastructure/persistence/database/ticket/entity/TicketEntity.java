package pl.wj.lotto.infrastructure.persistence.database.ticket.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "tickets")
public class TicketEntity {
    @Id
    String id;
    String userId;
    DrawingType drawingType;
    int numberOfDrawings;
    NumbersTemplate numbers;
    LocalDateTime generationTime;
}
