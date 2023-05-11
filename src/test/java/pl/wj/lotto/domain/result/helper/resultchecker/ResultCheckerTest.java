package pl.wj.lotto.domain.result.helper.resultchecker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultDto;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ResultCheckerTest {

    @InjectMocks
    private ResultChecker resultChecker;

    @Test
    void shouldReturnResultForLottoDraw() {
        // given
        String drawId = "some-draw-id";
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now())
                .numbers(numbers)
                .build();
        List<PlayerNumbersDto> ticketNumbers = new ArrayList<>();
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-1")
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build());
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-2")
                .mainNumbers(List.of(1,2,3,4,5,7))
                .build());
        Map<String, Integer> expectedResult = new HashMap<>();
        for (int level = 1; level <= 4; level++) {
            expectedResult.put(String.valueOf(level), 0);
        }
        expectedResult.put("1", 1);
        expectedResult.put("2", 1);

        // when
        Map<String, Integer> result = resultChecker.getResultsForDraw(drawResultDto, ticketNumbers);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultForQ600Draw() {
        // given
        String drawId = "some-draw-id";
        GameType gameType = GameType.Q600;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now())
                .numbers(numbers)
                .build();
        List<PlayerNumbersDto> ticketNumbers = new ArrayList<>();
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-1")
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build());
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-2")
                .mainNumbers(List.of(1,2,3,4,5,7))
                .build());
        Map<String, Integer> expectedResult = new HashMap<>();
        for (int level = 1; level <= 5; level++) {
            expectedResult.put(String.valueOf(level), 0);
        }
        expectedResult.put("1", 1);
        expectedResult.put("2", 1);

        // when
        Map<String, Integer> result = resultChecker.getResultsForDraw(drawResultDto, ticketNumbers);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultForEjpDraw() {
        // given
        String drawId = "some-draw-id";
        GameType gameType = GameType.EJP;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now())
                .numbers(numbers)
                .build();
        List<PlayerNumbersDto> ticketNumbers = new ArrayList<>();
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-1")
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build());
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-2")
                .mainNumbers(List.of(1,2,3,4,6))
                .extraNumbers(List.of(1,2))
                .build());
        Map<String, Integer> expectedResult = new HashMap<>();
        for (int level = 1; level <= 12; level++) {
            expectedResult.put(String.valueOf(level), 0);
        }
        expectedResult.put("1", 1);
        expectedResult.put("4", 1);

        // when
        Map<String, Integer> result = resultChecker.getResultsForDraw(drawResultDto, ticketNumbers);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultForKenoDraw() {
        // given
        String drawId = "some-draw-id";
        GameType gameType = GameType.KENO;
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20))
                .build();
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now())
                .numbers(numbers)
                .build();
        List<PlayerNumbersDto> ticketNumbers = new ArrayList<>();
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .userId("some-user-id-1")
                .mainNumbers(List.of(1,2,3,4,5))
                .build());
        ticketNumbers.add(PlayerNumbersDto.builder()
                .gameType(gameType)
                .drawId(drawId)
                .mainNumbers(List.of(1,2,3,4,21))
                .build());
        Map<String, Integer> expectedResult = new HashMap<>();
        for (int i = 1; i <= 10; i++)
            for (int j = 0; j <= i; j++)
                expectedResult.put(i + ";" + j, 0);
        expectedResult.put("5;4", 1);
        expectedResult.put("5;5", 1);

        // when
        Map<String, Integer> result = resultChecker.getResultsForDraw(drawResultDto, ticketNumbers);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultsForLottoTicket() {
        // given
        GameType gameType = GameType.LOTTO;
        LocalDateTime now = LocalDateTime.now();
        Numbers lottoNumbers1 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        Numbers lottoNumbers2 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,7))
                .build();
        Numbers ticketNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();

        List<Draw> draws = new ArrayList<>();
        draws.add(Draw.builder()
                .id("some-draw-id-1")
                .type(gameType)
                .drawDateTime(now)
                .numbers(lottoNumbers1)
                .build());
        draws.add(Draw.builder()
                .id("some-draw-id-1")
                .type(gameType)
                .drawDateTime(now.minusDays(1))
                .numbers(lottoNumbers2)
                .build());

        Ticket ticket = Ticket.builder()
                .id("some-ticket-id")
                .userId("some-user-id")
                .gameType(gameType)
                .numberOfDraws(2)
                .numbers(ticketNumbers)
                .generationDateTime(now.minusDays(2))
                .lastDrawDateTime(now)
                .build();

        List<TicketResultDto> expectedResult = new ArrayList<>();


        // when

        // then

    }

}