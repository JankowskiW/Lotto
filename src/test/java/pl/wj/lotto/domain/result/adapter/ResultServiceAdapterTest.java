package pl.wj.lotto.domain.result.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.wj.lotto.domain.common.drawdatetime.DrawDateTimeChecker;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbers.NumbersGenerator;
import pl.wj.lotto.domain.common.numbers.NumbersValidator;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.domain.draw.adapter.DrawServiceAdapter;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;
import pl.wj.lotto.domain.result.helper.resultchecker.ResultChecker;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;
import pl.wj.lotto.domain.result.service.ResultService;
import pl.wj.lotto.domain.ticket.adapter.TicketServiceAdapter;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;
import pl.wj.lotto.domain.user.adapter.UserServiceAdapter;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.domain.user.service.UserService;
import pl.wj.lotto.infrastructure.application.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.gametype.GameTypeFakeConfig;
import pl.wj.lotto.infrastructure.notification.fake.email.EmailNotificationFakeAdapter;
import pl.wj.lotto.infrastructure.notification.fake.sms.SMSNotificationFakeAdapter;
import pl.wj.lotto.infrastructure.numbersreceiver.fake.NumbersReceiverFakeAdapter;
import pl.wj.lotto.infrastructure.persistence.fake.draw.DrawFakeAdapter;
import pl.wj.lotto.infrastructure.persistence.fake.ticket.TicketFakeAdapter;
import pl.wj.lotto.infrastructure.persistence.fake.user.UserFakeAdapter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ResultServiceAdapterTest {
    private TicketRepositoryPort ticketRepositoryPort;
    private UserServicePort userServicePort;
    private DrawRepositoryPort drawRepositoryPort;
    private ResultServicePort resultServicePort;

    private Clock clock;

    private Ticket ticket;
    private Draw draw;

    @BeforeEach
    void setUp() {
        clock = new ClockFakeConfig().clock();
        ResultCheckerPort resultCheckerPort = new ResultChecker();
        ticketRepositoryPort = new TicketFakeAdapter();
        UserService userService = new UserService(new UserFakeAdapter(), new BCryptPasswordEncoder());
        userServicePort = new UserServiceAdapter(userService);
        GameTypeSettingsContainer gameTypeSettingsContainer = new GameTypeFakeConfig().gameTypeSettingsContainer();
        DrawDateTimeCheckerPort drawDateTimeCheckerPort = new DrawDateTimeChecker(clock, gameTypeSettingsContainer);
        NumbersReceiverPort numbersReceiverPort = new NumbersReceiverFakeAdapter();
        NumbersGeneratorPort numbersGeneratorPort = new NumbersGenerator(numbersReceiverPort, gameTypeSettingsContainer);
        NumbersValidatorPort numbersValidatorPort = new NumbersValidator(gameTypeSettingsContainer);
        NotificationPort emailNotificationPort = new EmailNotificationFakeAdapter();
        NotificationPort smsNotificationPort = new SMSNotificationFakeAdapter();
        TicketService ticketService = new TicketService(
                clock, ticketRepositoryPort, userServicePort,
                drawDateTimeCheckerPort, numbersGeneratorPort, numbersValidatorPort,
                emailNotificationPort, smsNotificationPort);
        TicketServicePort ticketServicePort = new TicketServiceAdapter(ticketService);
        drawRepositoryPort = new DrawFakeAdapter();
        DrawService drawService = new DrawService(clock, drawRepositoryPort, numbersGeneratorPort);
        DrawServicePort drawServicePort = new DrawServiceAdapter(drawService);
        ResultService resultService = new ResultService(resultCheckerPort, ticketServicePort, drawServicePort);
        resultServicePort = new ResultServiceAdapter(resultService);

        populateFakeDatabase();
    }

    private void populateFakeDatabase() {
        GameType gameType = GameType.LOTTO;
        LocalDateTime now = LocalDateTime.now(clock);
        String userId = "some-user-id";
        Numbers ticketNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        Numbers drawNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,7))
                .build();
        ticket = Ticket.builder()
                .userId(userId)
                .gameType(gameType)
                .drawsAmount(1)
                .numbers(ticketNumbers)
                .generationDateTime(now.minusDays(5))
                .lastDrawDateTime(now.minusDays(1))
                .build();
        draw = Draw.builder()
                .type(gameType)
                .drawDateTime(now.minusDays(1))
                .numbers(drawNumbers)
                .build();
        ticket = ticketRepositoryPort.save(ticket);
        draw = drawRepositoryPort.save(draw);
    }

    @Test
    void shouldReturnTicketResultDetailsByGivenId() {
        // given
        String ticketId = ticket.getId();
        TicketResultDto ticketResultDto =
                TicketResultDto.builder()
                        .drawId(draw.getId())
                        .ticketNumbers(ticket.getNumbers())
                        .winningNumbers(draw.getNumbers())
                        .level("2")
                        .prize(0.0)
                        .build();
        TicketResultsDetailsDto expectedResult =
                TicketResultsDetailsDto.builder()
                        .ticketId(ticketId)
                        .totalPrize(0.0)
                        .results(List.of(ticketResultDto))
                        .build();

        // when
        TicketResultsDetailsDto result = resultServicePort.getTicketResultsDetails(ticketId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawResultDetailsByGivenId() {
        // given
        String drawId = draw.getId();
        DrawResultDetailsResponseDto expectedResult = DrawResultDetailsResponseDto.builder()
                .drawId(drawId)
                .drawDateTime(draw.getDrawDateTime())
                .results(Map.of(
                        "1", 0,
                        "2", 1,
                        "3", 0,
                        "4", 0))
                .build();

        // when
        DrawResultDetailsResponseDto result = resultServicePort.getDrawResultDetails(drawId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}