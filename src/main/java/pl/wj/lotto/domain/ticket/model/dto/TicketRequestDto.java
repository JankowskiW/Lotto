package pl.wj.lotto.domain.ticket.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TicketRequestDto(
        String id,
        String userId,
        int gameTypeId,
        int numberOfDraws, // TODO: max 10 draws - do a validation
        boolean numbersAutogenerated,
        List<Integer> mainNumbers,
        List<Integer> extraNumbers
        ) {
}
