package pl.wj.lotto.domain.ticket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Ticket {
    String id;
    String userId;
    DrawingType drawingType;
    int numberOfDrawings;
    NumbersTemplate numbers;
    LocalDateTime generationTime;
}
