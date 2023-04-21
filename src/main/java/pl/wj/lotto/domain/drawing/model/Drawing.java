package pl.wj.lotto.domain.drawing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.wj.lotto.domain.common.DrawingType.DrawingType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Drawing {
    String id;
    DrawingType type;
    List<Integer> mainNumbers;
    List<Integer> extraNumbers;
    LocalDateTime drawingTime;
}
