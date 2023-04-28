package pl.wj.lotto.domain.ticket.mapper;

import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.drawingtype.DrawingTypeExtractor;
import pl.wj.lotto.domain.common.numberstemplate.NumberTemplateCreator;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.util.List;

public class TicketMapper {
    public static Ticket toTicket(TicketRequestDto ticketRequestDto) {
        DrawingType drawingType = DrawingTypeExtractor.getDrawingTypeById(ticketRequestDto.drawingTypeId());
        NumbersTemplate numbers = NumberTemplateCreator.createNumbersTemplateByDrawingType(drawingType);
        if (ticketRequestDto.mainNumbers() != null)
            numbers.setNumbers(ticketRequestDto.mainNumbers(), ticketRequestDto.extraNumbers());

        return Ticket.builder()
                .id(ticketRequestDto.id())
                .userId(ticketRequestDto.userId())
                .drawingType(drawingType)
                .numberOfDrawings(ticketRequestDto.numberOfDrawings())
                .numbers(numbers)
                .generationTime(null)
                .build();
    }

    public static List<TicketResponseDto> toTicketResponseDtos(List<Ticket> tickets) {
        return tickets.stream().map(TicketMapper::toTicketResponseDto).toList();
    }

    public static TicketResponseDto toTicketResponseDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .drawingTypeName(ticket.getDrawingType().getName())
                .numberOfDrawings(ticket.getNumberOfDrawings())
                .numbers(ticket.getNumbers())
                .generationTime(ticket.getGenerationTime())
                .nextDrawingTime(null)
                .build();
    }

    public static TicketResponseDto toTicketResponseDto(TicketRequestDto ticketRequestDto) {
        DrawingType drawingType = DrawingTypeExtractor.getDrawingTypeById(ticketRequestDto.drawingTypeId());
        NumbersTemplate numbers = NumberTemplateCreator.createNumbersTemplateByDrawingType(drawingType);
        numbers.setNumbers(ticketRequestDto.mainNumbers(), ticketRequestDto.extraNumbers());
        return TicketResponseDto.builder()
                .id(null)
                .userId(ticketRequestDto.userId())
                .drawingTypeName(drawingType.getName())
                .numberOfDrawings(ticketRequestDto.numberOfDrawings())
                .numbers(numbers)
                .generationTime(null)
                .nextDrawingTime(null)
                .build();
    }

    public static TicketEntity toTicketEntity(Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .drawingType(ticket.getDrawingType())
                .numberOfDrawings(ticket.getNumberOfDrawings())
                .numbers(ticket.getNumbers())
                .generationTime(ticket.getGenerationTime())
                .build();
    }

    public static List<Ticket> toTickets(List<TicketEntity> ticketEntities) {
        return ticketEntities.stream().map(TicketMapper::toTicket).toList();
    }

    public static Ticket toTicket(TicketEntity ticketEntity) {
        return Ticket.builder()
                .id(ticketEntity.getId())
                .userId(ticketEntity.getUserId())
                .drawingType(ticketEntity.getDrawingType())
                .numberOfDrawings(ticketEntity.getNumberOfDrawings())
                .numbers(ticketEntity.getNumbers())
                .generationTime(ticketEntity.getGenerationTime())
                .build();
    }
}
