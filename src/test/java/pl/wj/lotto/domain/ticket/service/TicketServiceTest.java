package pl.wj.lotto.domain.ticket.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.infrastructure.application.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.application.exception.definition.NumbersValidationException;
import pl.wj.lotto.infrastructure.application.exception.definition.ResourceNotFoundException;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private Clock clock;
    @Mock
    private TicketRepositoryPort ticketRepositoryPort;
    @Mock
    private NotificationPort notificationPort;
    @Mock
    private DrawDateTimeCheckerPort drawDateTimeCheckerPort;
    @Mock
    private NumbersGeneratorPort numbersGeneratorPort;
    @Mock
    private NumbersValidatorPort numbersValidatorPort;
    @InjectMocks
    private TicketService ticketService;

    private final Clock fixedClock = clock = new ClockFakeConfig().clock();

    @Test
    void shouldAddNewTicketWhenThereIsUserId() {
        // given
        GameType gameType = GameType.EJP;
        LocalDateTime generationDateTime = LocalDateTime.now();
        LocalDateTime nextDrawDateTime = LocalDateTime.now().plusDays(1);
        String userId = "some-user-id";
        String id = "some-id";
        TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                .userId(userId)
                .gameTypeId(gameType.getId())
                .drawsAmount(1)
                .numbersAutogenerated(true)
                .build();
        List<Integer> mainNumbers = List.of(1,2,3,4,5,6);
        List<Integer> extraNumbers = List.of(1,2);
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(mainNumbers)
                .extraNumbers(extraNumbers)
                .build();
        TicketResponseDto expectedResult = TicketResponseDto.builder()
                .id(id)
                .userId(userId)
                .gameTypeName(gameType.getName())
                .drawsAmount(1)
                .numbers(numbers)
                .generationDateTime(generationDateTime)
                .nextDrawDateTime(nextDrawDateTime)
                .build();
        given(clock.instant()).willReturn(fixedClock.instant());
        given(clock.getZone()).willReturn(fixedClock.getZone());
        given(numbersGeneratorPort.generate(any(GameType.class), anyInt(), anyBoolean())).willReturn(numbers);
        given(ticketRepositoryPort.save(any(Ticket.class))).willAnswer(
                i -> {
                    Ticket t = i.getArgument(0, Ticket.class);
                    t.setId(id);
                    t.setGenerationDateTime(generationDateTime);
                    return t;
                });
        given(drawDateTimeCheckerPort.getNextDrawDateTime(any(GameType.class))).willReturn(nextDrawDateTime);

        // when
        TicketResponseDto result = ticketService.addTicket(ticketRequestDto);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenGivenNumbersAreInvalid() {
        // given
        GameType gameType = GameType.LOTTO;
        String userId = "some-user-id";
        List<Integer> invalidMainNumbers = List.of(1,2,3,4,5,6,7);
        TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                .userId(userId)
                .gameTypeId(gameType.getId())
                .drawsAmount(1)
                .numbersAutogenerated(false)
                .mainNumbers(invalidMainNumbers)
                .build();
        given(numbersValidatorPort.validate(any(Numbers.class))).willReturn(false);

        // when && then
        assertThatThrownBy(() -> ticketService.addTicket(ticketRequestDto))
                .isInstanceOf(NumbersValidationException.class)
                .hasMessage("Given numbers are invalid");
    }

    @Test
    void shouldReturnUserTickets() {
        // given
        String userId = "some-user-id";
        GameType gameType = GameType.LOTTO;
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(Ticket.builder()
                        .id("some-ticket-id")
                        .userId(userId)
                        .gameType(gameType)
                        .drawsAmount(1)
                        .numbers(Numbers.builder().build())
                        .generationDateTime(LocalDateTime.now(fixedClock))
                        .lastDrawDateTime(LocalDateTime.now(fixedClock))
                .build());
        List<TicketResponseDto> expectedResult = TicketMapper.toTicketResponseDtos(tickets);
        given(ticketRepositoryPort.getByUserId(anyString())).willReturn(tickets);

        // when
        List<TicketResponseDto> result = ticketService.getUserTickets(userId);

        // then
        assertThat(result)
                .containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @Test
    void shouldReturnPlayersDrawNumbers() {
        // given
        LocalDateTime now = LocalDateTime.now(fixedClock);
        GameType gameType = GameType.LOTTO;
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .numbers(Numbers.builder().build())
                .drawDateTime(now)
                .build();
        List<TicketEntity> ticketEntities = new ArrayList<>();
        ticketEntities.add(TicketEntity.builder()
                        .id("some-ticket-id")
                        .userId("some-user-id")
                        .gameType(gameType)
                        .drawsAmount(1)
                        .numbers(Numbers.builder().build())
                        .generationDateTime(now.minusHours(2))
                        .lastDrawDateTime(now.plusHours(2))
                .build());
        List<PlayerNumbersDto> expectedResult = TicketMapper.toPlayerNumbersDtos(ticketEntities);
        given(ticketRepositoryPort.getPlayersDrawNumbersByGameTypeAndLastDrawDateTime(
                any(GameType.class), any(LocalDateTime.class))).willReturn(ticketEntities);

        // when
        List<PlayerNumbersDto> result = ticketService.getPlayersDrawNumbers(drawResultDto);

        // then
        assertThat(result)
                .containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @Test
    void shouldReturnTicketByIdWhenExistsInDatabase() {
        // given
        LocalDateTime now = LocalDateTime.now(fixedClock);
        GameType gameType = GameType.LOTTO;
        String ticketId = "some-ticket-id";
        TicketEntity ticketEntity = TicketEntity.builder()
                .id(ticketId)
                .userId("some-user-id")
                .gameType(gameType)
                .drawsAmount(1)
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .generationDateTime(now)
                .lastDrawDateTime(now.plusDays(1))
                .build();
        Ticket expectedResult = TicketMapper.toTicket(ticketEntity);
        given(ticketRepositoryPort.findById(anyString())).willReturn(Optional.of(ticketEntity));

        // when
        Ticket result = ticketService.getTicket(ticketId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenTicketWithGivenIdNotExistsInDatabase() {
        // given
        String ticketId = "some-ticket-id";
        given(ticketRepositoryPort.findById(anyString())).willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> ticketService.getTicket(ticketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Ticket not found");
    }
}