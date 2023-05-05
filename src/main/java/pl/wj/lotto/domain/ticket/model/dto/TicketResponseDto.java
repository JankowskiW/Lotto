package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;
import lombok.With;
import pl.wj.lotto.domain.common.numbers.model.Numbers;

import java.time.LocalDateTime;

@Builder
@With
public record TicketResponseDto(
        String id,
        String userId,
        String gameTypeName,
        int numberOfDraws,
        Numbers numbers,
        LocalDateTime nextDrawDateTime,
        LocalDateTime generationDateTime
        ) {
}
