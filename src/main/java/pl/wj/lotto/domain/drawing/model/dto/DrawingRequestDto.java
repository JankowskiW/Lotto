package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;
import pl.wj.lotto.domain.common.numberstemplate.model.NumbersTemplate;

@Builder
public record DrawingRequestDto (
        String type,
        NumbersTemplate numbers) {
}
