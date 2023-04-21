package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DrawingResponseDto {
    String id;
    String type;
    List<Integer> mainNumbers;
    List<Integer> extraNumbers;
    LocalDateTime drawingTime;
}
