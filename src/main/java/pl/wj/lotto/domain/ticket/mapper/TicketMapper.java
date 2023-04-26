package pl.wj.lotto.domain.ticket.mapper;

import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.model.EuroJackpotNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.KenoNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.LottoNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.Quick600Numbers;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.util.stream.Stream;

public class TicketMapper {
    public static Ticket toTicket(TicketRequestDto ticketRequestDto) {
        // TODO: create file in drawingtype package and put that fragment of code in there
        DrawingType drawingType = Stream.of(DrawingType.values())
                .filter(dt -> dt.getId() == ticketRequestDto.drawingTypeId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));

        // TODO: create file in numbertemplate package and put that fragment of code in there
        NumbersTemplate numbers;
        switch(drawingType) {
            case LOTTO -> numbers = new LottoNumbers();
            case Q600 -> numbers = new Quick600Numbers();
            case EJP -> numbers = new EuroJackpotNumbers();
            case KENO -> numbers = new KenoNumbers();
            default -> numbers = null;
        }
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

    public static TicketResponseDto toTicketResponseDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .drawingTypeName(ticket.getDrawingType().getName())
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
