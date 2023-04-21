package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;

@Builder
public record TicketRequestDto(
        String id,
        String userId,
        String drawingTypeId) {
}
