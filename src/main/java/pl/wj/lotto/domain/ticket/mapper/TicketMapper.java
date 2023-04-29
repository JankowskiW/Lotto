package pl.wj.lotto.domain.ticket.mapper;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeExtractor;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.Numbers;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.util.List;

public class TicketMapper {
    public static Ticket toTicket(TicketRequestDto ticketRequestDto) {
        GameType gameType = GameTypeExtractor.getGameTypeById(ticketRequestDto.gameTypeId());
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .drawTime(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawTime())
                .mainNumbers(ticketRequestDto.mainNumbers())
                .extraNumbers(ticketRequestDto.extraNumbers())
                .build();

        return Ticket.builder()
                .id(ticketRequestDto.id())
                .userId(ticketRequestDto.userId())
                .gameType(gameType)
                .numberOfDraws(ticketRequestDto.numberOfDraws())
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
                .gameTypeName(ticket.getGameType().getName())
                .numberOfDraws(ticket.getNumberOfDraws())
                .numbers(ticket.getNumbers())
                .generationTime(ticket.getGenerationTime())
                .nextDrawTime(null)
                .build();
    }

    public static TicketResponseDto toTicketResponseDto(TicketRequestDto ticketRequestDto) {
        GameType gameType = GameTypeExtractor.getGameTypeById(ticketRequestDto.gameTypeId());
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .drawTime(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawTime())
                .mainNumbers(ticketRequestDto.mainNumbers())
                .extraNumbers(ticketRequestDto.extraNumbers())
                .build();
        return TicketResponseDto.builder()
                .id(null)
                .userId(ticketRequestDto.userId())
                .gameTypeName(gameType.getName())
                .numberOfDraws(ticketRequestDto.numberOfDraws())
                .numbers(numbers)
                .generationTime(null)
                .nextDrawTime(null)
                .build();
    }

    public static TicketEntity toTicketEntity(Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .gameType(ticket.getGameType())
                .numberOfDraws(ticket.getNumberOfDraws())
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
                .gameType(ticketEntity.getGameType())
                .numberOfDraws(ticketEntity.getNumberOfDraws())
                .numbers(ticketEntity.getNumbers())
                .generationTime(ticketEntity.getGenerationTime())
                .build();
    }
}
