package pl.wj.lotto.domain.ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.infrastructure.application.exception.definition.NumbersValidationException;
import pl.wj.lotto.infrastructure.application.exception.definition.ResourceNotFoundException;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class TicketService {
    private final Clock clock;
    private final TicketRepositoryPort ticketRepositoryPort;
    private final UserServicePort userServicePort;
    private final DrawDateTimeCheckerPort drawDateTimeCheckerPort;
    private final NumbersGeneratorPort numbersGeneratorPort;
    private final NumbersValidatorPort numbersValidatorPort;
    private final NotificationPort emailNotificationPort;
    private final NotificationPort smsNotificationPort;

    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = TicketMapper.toTicket(ticketRequestDto);
        if (ticketRequestDto.numbersAutogenerated()) {
            Numbers numbers;
            if (ticket.getGameType().equals(GameType.KENO)) {
                numbers = numbersGeneratorPort.generate(ticket.getGameType(), ticketRequestDto.generatedNumbersAmount(), true);
            }else {
                numbers = numbersGeneratorPort.generate(ticket.getGameType(), true);
            }
            ticket.setNumbers(numbers);
        } else if (!numbersValidatorPort.validate(ticket.getNumbers())) {
            throw new NumbersValidationException("Given numbers are invalid");
        }

        ticket.setGenerationDateTime(LocalDateTime.now(clock));
        LocalDateTime lastDrawDateTime = drawDateTimeCheckerPort.getLastDrawDateTimeForTicket(ticket.getGameType(), ticket.getDrawsAmount(), ticket.getGenerationDateTime());
        ticket.setLastDrawDateTime(lastDrawDateTime);
        ticket.setGenerationDateTime(LocalDateTime.now(clock));
        ticket = ticketRepositoryPort.save(ticket);

        try {
            User user = userServicePort.getUserById(ticket.getUserId());
            String message = "You have just bought a new ticket";
            emailNotificationPort.send(user.emailAddress(), message);
            if (user.phoneNumber() != null && !user.phoneNumber().isBlank()) {
                smsNotificationPort.send(user.phoneNumber(), message);
            }
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("Cannot find user by given id but ticket has been generated");
        }
        TicketResponseDto ticketResponseDto = TicketMapper.toTicketResponseDto(ticket);
        LocalDateTime nextDrawDateTime = drawDateTimeCheckerPort.getNextDrawDateTime(ticket.getGameType());
        return ticketResponseDto.withNextDrawDateTime(nextDrawDateTime);
    }

    public List<TicketResponseDto> getUserTickets(String userId) {
        List<Ticket> tickets = ticketRepositoryPort.getByUserId(userId);
        List<TicketResponseDto> ticketResponseDtos = TicketMapper.toTicketResponseDtos(tickets);
        return ticketResponseDtos.stream().map(tr -> tr.withNextDrawDateTime(
                drawDateTimeCheckerPort.getNextDrawDateTimeForTicket(tr.numbers().gameType(), tr.generationDateTime()))).toList();
    }

    public List<PlayerNumbersDto> getPlayersDrawNumbers(DrawWinningNumbersDto drawWinningNumbersDto) {
        LocalDateTime drawDateTime = drawWinningNumbersDto.drawDateTime().truncatedTo(ChronoUnit.MINUTES);
        List<TicketEntity> ticketEntities = ticketRepositoryPort.getPlayersDrawNumbersByGameTypeAndLastDrawDateTime(
                drawWinningNumbersDto.type(), drawDateTime);
        return TicketMapper.toPlayerNumbersDtos(ticketEntities);
    }

    public Ticket getTicket(String ticketId) {
        TicketEntity ticketEntity = ticketRepositoryPort.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        return TicketMapper.toTicket(ticketEntity);
    }
}
