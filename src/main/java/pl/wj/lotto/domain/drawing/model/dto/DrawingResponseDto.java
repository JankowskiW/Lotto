package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DrawingResponseDto (
    String id,
    String type,
    List<Integer> mainNumbers,
    List<Integer> extraNumbers,
    LocalDateTime drawingTime) {
}
