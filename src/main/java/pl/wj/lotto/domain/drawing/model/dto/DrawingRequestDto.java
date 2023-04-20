package pl.wj.lotto.domain.drawing.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DrawingRequestDto {
    String type;
    List<Integer> mainNumbers;
    List<Integer> extraNumbers;
}
