package pl.wj.lotto.domain.draw.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.NumbersGenerator;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.numbersreceiver.fake.NumbersReceiverFakeAdapter;
import pl.wj.lotto.infrastructure.persistence.fake.draw.DrawFakeAdapter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DrawServiceAdapterComponentTest {
    private DrawRepositoryPort drawRepositoryPort;
    private DrawServicePort drawServicePort;
    private Clock clock;


    @BeforeEach
    void setUp() {
        clock = new ClockFakeConfig().clock();
        drawRepositoryPort = new DrawFakeAdapter();
        NumbersReceiverPort numbersReceiverPort = new NumbersReceiverFakeAdapter();
        NumbersGeneratorPort numbersGeneratorPort = new NumbersGenerator(numbersReceiverPort);
        drawServicePort = new DrawServiceAdapter(new DrawService(clock, drawRepositoryPort, numbersGeneratorPort));
    }

    @Test
    void shouldReturnDrawResponseDtosOfSpecificDrawType() {
        // given
        int expectedSize = 2;
        GameType gameType = GameType.EJP;
        Numbers numbersEJP = Numbers.builder()
                .gameType(gameType)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        drawRepositoryPort.save(Draw.builder().type(gameType).numbers(numbersEJP).build());
        numbersEJP = Numbers.builder()
                .gameType(gameType)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,3))
                .build();
        drawRepositoryPort.save(Draw.builder().type(gameType).numbers(numbersEJP).build());
        Numbers numbersLotto = Numbers.builder()
                .gameType(GameType.LOTTO)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(GameType.LOTTO).drawDateTimeSettings())
                .mainNumbers(List.of(1,2,3,4,5,8))
                .build();
        drawRepositoryPort.save(Draw.builder().type(GameType.LOTTO).numbers(numbersLotto).build());
        List<String> unwantedDrawTypes =
                Stream.of(GameType.values())
                        .filter(dt -> !dt.equals(gameType))
                        .map(GameType::getName)
                        .toList();

        // when
        List<DrawResponseDto> result = drawServicePort.getGameTypeDraws(gameType.getId());

        // then
        assertAll(
                () -> assertThat(result).isNotNull().hasSize(expectedSize),
                () -> assertThat(result.stream().map(DrawResponseDto::typeName).toList())
                        .doesNotContainAnyElementsOf(unwantedDrawTypes)
        );
    }

    @Test
    void shouldSaveNewDrawAndReturnDrawResponseDto() {
        // given
        GameType gameType = GameType.LOTTO;

        // when
        DrawResponseDto result = drawServicePort.addDraw(gameType);

        // then
        assertAll(
                () -> assertThat(result.typeName()).isEqualTo(gameType.getName()),
                () -> assertThat(result.numbers().mainNumbers()).hasSize(6),
                () -> assertThat(result.numbers().extraNumbers()).isNull()
        );
    }

    @Test
    void shouldReturnDrawResponseDtoByDrawId() {
        // given
        GameType gameType = GameType.LOTTO;
        List<Integer> mainNumbers = List.of(1,2,3,4,5,6);
        Numbers numbersLotto = Numbers.builder()
                .gameType(gameType)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(mainNumbers)
                .build();
        Draw draw = Draw.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now(clock))
                .numbers(numbersLotto)
                .build();
        draw = drawRepositoryPort.save(draw);
        DrawResponseDto expectedResult = DrawResponseDto.builder()
                .id(draw.getId())
                .typeName(gameType.getName())
                .drawDateTime(LocalDateTime.now(clock))
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                        .mainNumbers(mainNumbers)
                        .build())
                .build();

        // when
        DrawResponseDto result = drawServicePort.getDraw(draw.getId());

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawResultByDrawId() {
        // given
        GameType gameType = GameType.LOTTO;
        List<Integer> mainNumbers = List.of(1,2,3,4,5,6);
        Numbers numbersLotto = Numbers.builder()
                .gameType(gameType)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(mainNumbers)
                .build();
        Draw draw = Draw.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now(clock))
                .numbers(numbersLotto)
                .build();
        draw = drawRepositoryPort.save(draw);
        DrawResultDto expectedResult = DrawMapper.toDrawResultDto(draw);

        // when
        DrawResultDto result = drawServicePort.getDrawResult(draw.getId());

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawListForGivenTicket() {
        // given
        LocalDateTime now = LocalDateTime.now(clock);
        GameType gameType = GameType.LOTTO;
        Ticket ticket = Ticket.builder()
                .id("some-ticket-id")
                .userId("some-user-id")
                .gameType(gameType)
                .numberOfDraws(2)
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .generationDateTime(now.minusDays(6))
                .lastDrawDateTime(now.minusDays(1))
                .build();
        Draw firstDraw = Draw.builder()
                .type(gameType)
                .drawDateTime(now.minusDays(5))
                .numbers(Numbers.builder().build())
                .build();
        firstDraw = drawRepositoryPort.save(firstDraw);
        Draw secondDraw = Draw.builder()
                .type(gameType)
                .drawDateTime(now.minusDays(1))
                .numbers(Numbers.builder().build())
                .build();
        secondDraw = drawRepositoryPort.save(secondDraw);
        List<Draw> expectedResult = List.of(firstDraw, secondDraw);

        // when
        List<Draw> result = drawServicePort.getDrawsForTicket(ticket);

        // then
        assertThat(result)
                .containsExactlyInAnyOrderElementsOf(expectedResult);
    }

}