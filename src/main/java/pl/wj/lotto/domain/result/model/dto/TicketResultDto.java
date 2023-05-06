package pl.wj.lotto.domain.result.model.dto;

import lombok.Builder;
import pl.wj.lotto.domain.common.numbers.model.Numbers;

@Builder
public record TicketResultDto (
        String drawId,
        String level,
        Numbers winningNumbers,
        Numbers ticketNumbers,
        Double prize
)
{}
