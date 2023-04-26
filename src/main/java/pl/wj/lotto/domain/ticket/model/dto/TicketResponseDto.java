package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TicketResponseDto(
        String id,
        String userId,
        String drawingTypeName,
        int numberOfDrawings,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers,
        LocalDateTime nextDrawingTime
        ) {
}
