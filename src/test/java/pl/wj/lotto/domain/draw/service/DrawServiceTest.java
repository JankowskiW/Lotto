package pl.wj.lotto.domain.draw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.infrastructure.application.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.application.exception.definition.ResourceNotFoundException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DrawServiceTest {
    @Mock
    private Clock clock;
    @Mock
    private DrawRepositoryPort drawRepositoryPort;
    @Mock
    private NumbersGeneratorPort numbersGeneratorPort;
    @InjectMocks
    private DrawService drawService;

    private final Clock fixedClock = clock = new ClockFakeConfig().clock();

    @Test
    void shouldReturnEmptyListWhenThereIsNoDrawWithGivenType() {
        // given
        GameType gameType = GameType.EJP;
        given(drawRepositoryPort.findAllByType(any(GameType.class))).willReturn(new ArrayList<>());

        // when
        List<DrawResponseDto> result = drawService.getGameTypeDraws(gameType.getId());

        // then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void shouldReturnDrawsWithGivenType() {
        // given
        GameType gameType = GameType.EJP;
        List<Draw> draws = new ArrayList<>();
        List<DrawResponseDto> expectedResult = DrawMapper.toDrawResponseDtos(draws);
        given(drawRepositoryPort.findAllByType(any(GameType.class))).willReturn(draws);

        // when
        List<DrawResponseDto> result = drawService.getGameTypeDraws(gameType.getId());

        // then
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldAddDraw() {
        // given
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDateTime = LocalDateTime.now();
        GameType gameType = GameType.EJP;
        DrawRequestDto drawRequestDto = DrawRequestDto.builder()
                .typeId(gameType.getId())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        Draw draw = DrawMapper.toDraw(drawRequestDto);
        draw.setId(id);
        draw.setDrawDateTime(drawDateTime);
        DrawResponseDto expectedResult = DrawMapper.toDrawResponseDto(draw);
        given(clock.instant()).willReturn(fixedClock.instant());
        given(clock.getZone()).willReturn(fixedClock.getZone());
        given(numbersGeneratorPort.generate(any(GameType.class),anyBoolean()))
                .willReturn(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(drawRequestDto.mainNumbers())
                        .extraNumbers(drawRequestDto.extraNumbers())
                        .build());
        given(drawRepositoryPort.save(any(Draw.class)))
                .willAnswer(
                        i -> {
                            Draw d = i.getArgument(0, Draw.class);
                            d.setId(id);
                            d.setDrawDateTime(drawDateTime);
                            return d;
                        }
                );

        // when
        DrawResponseDto result = drawService.addDraw(gameType);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawByIdWhenAnyExistsInDatabase() {
        // given
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDateTime = LocalDateTime.now();
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        Draw draw = Draw.builder()
                .id(id)
                .type(gameType)
                .numbers(numbers)
                .drawDateTime(drawDateTime)
                .build();
        DrawResponseDto expectedResult = DrawMapper.toDrawResponseDto(draw);
        given(drawRepositoryPort.findById(anyString())).willReturn(Optional.of(draw));

        // when
        DrawResponseDto result = drawService.getDraw(id);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenDrawNotExistsInDabase() {
        // given
        String id = UUID.randomUUID().toString();
        given(drawRepositoryPort.findById(anyString())).willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> drawService.getDraw(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Draw not found");
    }

    @Test
    void shouldReturnDrawResultByDrawIdWhenAnyExists() {
        // given
        GameType gameType = GameType.LOTTO;
        String drawId = "some-draw-id";
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now(fixedClock))
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .build();
        DrawResultDto expectedResult = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now(fixedClock))
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .build();
        given(drawRepositoryPort.findDrawResultById(anyString())).willReturn(Optional.of(drawResultDto));

        // when
        DrawResultDto result = drawService.getDrawResult(drawId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenDrawNotExistsInDatabse() {
        // given
        String drawId = "some-draw-id";
        given(drawRepositoryPort.findDrawResultById(anyString())).willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> drawService.getDrawResult(drawId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Draw not found");
    }

    @Test
    void shouldReturnDrawListForGivenTicket() {
        // given
        LocalDateTime now = LocalDateTime.now(fixedClock);
        GameType gameType = GameType.LOTTO;
        Ticket ticket = Ticket.builder()
                .id("some-ticket-id")
                .userId("some-user-id")
                .gameType(gameType)
                .drawsAmount(2)
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .generationDateTime(now.minusDays(6))
                .lastDrawDateTime(now.minusDays(1))
                .build();
        Draw firstDraw = Draw.builder()
                .id("some-draw-id-1")
                .type(gameType)
                .drawDateTime(now.minusDays(5))
                .numbers(Numbers.builder().build())
                .build();
        Draw secondDraw = Draw.builder()
                .id("some-draw-id-2")
                .type(gameType)
                .drawDateTime(now.minusDays(1))
                .numbers(Numbers.builder().build())
                .build();
        List<Draw> expectedResult = List.of(firstDraw, secondDraw);
        given(drawRepositoryPort.findAllByTypeAndDrawDateTime(any(GameType.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(firstDraw, secondDraw));

        // when
        List<Draw> result = drawService.getDrawsForTicket(ticket);

        // then
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedResult);
    }

}