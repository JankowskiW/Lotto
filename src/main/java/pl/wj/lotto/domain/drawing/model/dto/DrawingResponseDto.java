package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DrawingResponseDto {
    String id;
    String type;
    List<Integer> mainNumbers;
    List<Integer> extraNumbers;
    LocalDateTime drawingTime;
}
