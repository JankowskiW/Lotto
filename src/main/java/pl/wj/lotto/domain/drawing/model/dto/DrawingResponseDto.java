package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;

import java.time.LocalDateTime;

@Builder
public record DrawingResponseDto (
    String id,
    String type,
    NumbersTemplate numbers,
    LocalDateTime drawingTime) {
}
