package pl.wj.lotto.domain.ticket.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawdatetime.DrawDateTimeChecker;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbers.NumbersGenerator;
import pl.wj.lotto.domain.common.numbers.NumbersValidator;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.notification.fake.email.EmailNotificationFakeAdapter;
import pl.wj.lotto.infrastructure.numbersreceiver.fake.NumbersReceiverFakeAdapter;
import pl.wj.lotto.infrastructure.persistence.fake.ticket.TicketFakeAdapter;

import java.time.Clock;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TicketServiceAdapterComponentTest {
    private TicketRepositoryPort ticketRepositoryPort;
    private TicketServiceAdapter ticketServiceAdapter;

    @BeforeEach
    void setUp() {
        Clock clock = new ClockFakeConfig().clock();
        NotificationPort notificationPort = new EmailNotificationFakeAdapter();
        NumbersReceiverPort numbersReceiverPort = new NumbersReceiverFakeAdapter();
        NumbersGeneratorPort numbersGeneratorPort = new NumbersGenerator(numbersReceiverPort);
        NumbersValidatorPort numbersValidatorPort = new NumbersValidator();
        DrawDateTimeCheckerPort drawDateTimeCheckerPort = new DrawDateTimeChecker(clock);
        ticketRepositoryPort = new TicketFakeAdapter();
        TicketService ticketService = new TicketService(
                ticketRepositoryPort, notificationPort, drawDateTimeCheckerPort, numbersGeneratorPort, numbersValidatorPort);
        ticketServiceAdapter = new TicketServiceAdapter(ticketService);
    }

    @Test
    void shouldAddNewTicketWhenAnonymousUserHasChosenNumbers() {
        // given
        GameType gameType = GameType.LOTTO;
        List<Integer> mainNumbers = List.of(1,2,3,4,5,6);
        TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                .userId(null)
                .gameTypeId(gameType.getId())
                .numberOfDraws(1)
                .numbersAutogenerated(false)
                .mainNumbers(mainNumbers)
                .extraNumbers(null)
                .build();
        TicketResponseDto expectedResult = TicketMapper.toTicketResponseDto(ticketRequestDto);

        // when
        TicketResponseDto result = ticketServiceAdapter.addTicket(ticketRequestDto);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.gameTypeName()).isEqualTo(expectedResult.gameTypeName()),
                () -> assertThat(result.numberOfDraws()).isEqualTo(expectedResult.numberOfDraws()),
                () -> assertThat(result.numbers().mainNumbers()).isEqualTo(expectedResult.numbers().mainNumbers()),
                () -> assertThat(result.numbers().extraNumbers()).isEqualTo(expectedResult.numbers().extraNumbers())
        );
    }

    @Test
    void shouldReturnTicketResponseDtoListOfSpecificUser() {
        // given
        String userId = "some-user-id";
        TicketRequestDto ticketRequestDto;
        ticketRequestDto = TicketRequestDto.builder()
                .userId("")
                .gameTypeId(GameType.LOTTO.getId())
                .numberOfDraws(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));
        ticketRequestDto = TicketRequestDto.builder()
                .userId("some-other-user-id")
                .gameTypeId(GameType.LOTTO.getId())
                .numberOfDraws(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));
        ticketRequestDto = TicketRequestDto.builder()
                .userId(userId)
                .gameTypeId(GameType.LOTTO.getId())
                .numberOfDraws(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));
        ticketRequestDto = TicketRequestDto.builder()
                .userId(userId)
                .gameTypeId(GameType.LOTTO.getId())
                .numberOfDraws(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));

        // when
        List<TicketResponseDto> result = ticketServiceAdapter.getTicketsByUserId(userId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull().hasSize(2),
                () -> assertThat(result.stream().map(TicketResponseDto::userId).toList()).containsOnly(userId)
        );
    }

}