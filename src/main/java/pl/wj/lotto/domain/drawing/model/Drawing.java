package pl.wj.lotto.domain.drawing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Drawing {
    String id;
    DrawingType type;
    NumbersTemplate numbers;
    LocalDateTime drawingTime;
}
