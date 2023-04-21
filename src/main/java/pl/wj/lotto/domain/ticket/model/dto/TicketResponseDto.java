package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;

@Builder
public record TicketResponseDto(
        String id,
        String userId,
        String drawingTypeId) {
}
