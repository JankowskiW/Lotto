package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TicketRequestDto(
        String id,
        String userId,
        String drawingTypeId,
        int numberOfDrawings,
        boolean userNumbers,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers
        ) {
}
