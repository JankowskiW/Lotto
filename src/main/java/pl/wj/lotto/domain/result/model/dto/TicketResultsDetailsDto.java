package pl.wj.lotto.domain.result.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TicketResultsDetailsDto(
        String ticketId,
        List<TicketResultDto> results,
        Double totalPrize
) {}
