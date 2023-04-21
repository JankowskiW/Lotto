package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record DrawingRequestDto (
        String type,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers) {
}
