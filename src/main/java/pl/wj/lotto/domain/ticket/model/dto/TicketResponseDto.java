package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;
import lombok.With;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;

import java.time.LocalDateTime;

@Builder
@With
public record TicketResponseDto(
        String id,
        String userId,
        String drawingTypeName,
        int numberOfDrawings,
        NumbersTemplate numbers,
        LocalDateTime nextDrawingTime,
        LocalDateTime generationTime
        ) {
}
