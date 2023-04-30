package pl.wj.lotto.domain.draw.model.dto;

import lombok.Builder;
import pl.wj.lotto.domain.common.numbers.Numbers;

import java.time.LocalDateTime;

@Builder
public record DrawResponseDto(
    String id,
    String typeName,
    Numbers numbers,
    LocalDateTime drawDateTime) {
}
