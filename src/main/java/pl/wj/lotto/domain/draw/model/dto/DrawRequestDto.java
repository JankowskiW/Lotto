package pl.wj.lotto.domain.draw.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record DrawRequestDto(
        int typeId,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers) {
}
