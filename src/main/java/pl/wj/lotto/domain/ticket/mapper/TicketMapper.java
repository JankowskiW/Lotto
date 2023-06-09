package pl.wj.lotto.domain.ticket.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeParser;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {
    public static Ticket toTicket(TicketRequestDto ticketRequestDto) {
        GameType gameType = GameTypeParser.getGameTypeById(ticketRequestDto.gameTypeId());
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(ticketRequestDto.mainNumbers())
                .extraNumbers(ticketRequestDto.extraNumbers())
                .build();

        return Ticket.builder()
                .userId(ticketRequestDto.userId())
                .gameType(gameType)
                .drawsAmount(ticketRequestDto.drawsAmount())
                .numbers(numbers)
                .generationDateTime(null)
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
                .drawsAmount(ticket.getDrawsAmount())
                .numbers(ticket.getNumbers())
                .generationDateTime(ticket.getGenerationDateTime())
                .nextDrawDateTime(null)
                .build();
    }

    public static TicketResponseDto toTicketResponseDto(TicketEntity ticketEntity) {
        return TicketResponseDto.builder()
                .id(ticketEntity.getId())
                .userId(ticketEntity.getUserId())
                .gameTypeName(ticketEntity.getGameType().getName())
                .drawsAmount(ticketEntity.getDrawsAmount())
                .numbers(ticketEntity.getNumbers())
                .generationDateTime(ticketEntity.getGenerationDateTime())
                .nextDrawDateTime(null)
                .build();
    }

    public static TicketResponseDto toTicketResponseDto(TicketRequestDto ticketRequestDto) {
        GameType gameType = GameTypeParser.getGameTypeById(ticketRequestDto.gameTypeId());
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(ticketRequestDto.mainNumbers())
                .extraNumbers(ticketRequestDto.extraNumbers())
                .build();
        return TicketResponseDto.builder()
                .id(null)
                .userId(ticketRequestDto.userId())
                .gameTypeName(gameType.getName())
                .drawsAmount(ticketRequestDto.drawsAmount())
                .numbers(numbers)
                .generationDateTime(null)
                .nextDrawDateTime(null)
                .build();
    }

    public static List<TicketEntity> toTicketEntities(List<Ticket> tickets) {
        return tickets.stream().map(TicketMapper::toTicketEntity).toList();
    }

    public static TicketEntity toTicketEntity(Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .gameType(ticket.getGameType())
                .drawsAmount(ticket.getDrawsAmount())
                .numbers(ticket.getNumbers())
                .generationDateTime(ticket.getGenerationDateTime())
                .lastDrawDateTime(ticket.getLastDrawDateTime())
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
                .drawsAmount(ticketEntity.getDrawsAmount())
                .numbers(ticketEntity.getNumbers())
                .generationDateTime(ticketEntity.getGenerationDateTime())
                .lastDrawDateTime(ticketEntity.getLastDrawDateTime())
                .build();
    }

    public static List<PlayerNumbersDto> toPlayerNumbersDtos(List<TicketEntity> ticketEntities) {
        return ticketEntities.stream().map(TicketMapper::toPlayerNumbersDto).toList();
    }

    public static PlayerNumbersDto toPlayerNumbersDto(TicketEntity ticketEntity) {
        return PlayerNumbersDto.builder()
                .userId(ticketEntity.getUserId())
                .gameType(ticketEntity.getGameType())
                .mainNumbers(ticketEntity.getNumbers().mainNumbers())
                .extraNumbers(ticketEntity.getNumbers().extraNumbers())
                .build();
    }

    public static PlayerNumbersDto toPlayerNumbersDto(Ticket ticket) {
        return PlayerNumbersDto.builder()
                .userId(ticket.getUserId())
                .gameType(ticket.getGameType())
                .mainNumbers(ticket.getNumbers().mainNumbers())
                .extraNumbers(ticket.getNumbers().extraNumbers())
                .build();
    }
}
