package pl.wj.lotto.domain.ticket.mapper;

import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;

public class TicketMapper {
    public static Ticket toTicket(TicketRequestDto ticketRequestDto) {
        return Ticket.builder()
                .id(ticketRequestDto.id())
                .userId(ticketRequestDto.userId())
                .drawingTypeId(ticketRequestDto.drawingTypeId())
                .build();
    }

    public static TicketResponseDto toTicketResponseDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .drawingTypeId(ticket.getDrawingTypeId())
                .build();
    }
}
