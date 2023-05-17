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
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
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
        String drawId1 = "some-draw-id-1";
        String drawId2 = "some-draw-id-2";
        String ticketId = "some-ticket-id";
        String userId = "some-user-id";

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

        List<Draw> ticketDraws = new ArrayList<>();
        ticketDraws.add(Draw.builder()
                .id(drawId1)
                .type(gameType)
                .drawDateTime(now)
                .numbers(lottoNumbers1)
                .build());
        ticketDraws.add(Draw.builder()
                .id(drawId2)
                .type(gameType)
                .drawDateTime(now.minusDays(1))
                .numbers(lottoNumbers2)
                .build());

        Ticket ticket = Ticket.builder()
                .id(ticketId)
                .userId(userId)
                .gameType(gameType)
                .drawsAmount(2)
                .numbers(ticketNumbers)
                .generationDateTime(now.minusDays(2))
                .lastDrawDateTime(now)
                .build();

        List<TicketResultDto> ticketResultDtos = new ArrayList<>();
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId1)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(lottoNumbers1)
                .level("1")
                .prize(0.0)
                .build());
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId2)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(lottoNumbers2)
                .level("2")
                .prize(0.0)
                .build());

        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(ticketResultDtos)
                .totalPrize(0.0)
                .build();

        // when
        TicketResultsDetailsDto result = resultChecker.getResultsForTicket(ticket, ticketDraws);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultsForQ600Ticket() {
        // given
        GameType gameType = GameType.Q600;
        LocalDateTime now = LocalDateTime.now();
        String drawId = "some-draw-id";
        String ticketId = "some-ticket-id";
        String userId = "some-user-id";

        Numbers q600Numbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        Numbers ticketNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,7,8,9))
                .build();

        List<Draw> ticketDraws = new ArrayList<>();
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now)
                .numbers(q600Numbers)
                .build());

        Ticket ticket = Ticket.builder()
                .id(ticketId)
                .userId(userId)
                .gameType(gameType)
                .drawsAmount(1)
                .numbers(ticketNumbers)
                .generationDateTime(now.minusDays(2))
                .lastDrawDateTime(now)
                .build();

        List<TicketResultDto> ticketResultDtos = new ArrayList<>();
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(q600Numbers)
                .level("4")
                .prize(0.0)
                .build());

        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(ticketResultDtos)
                .totalPrize(0.0)
                .build();

        // when
        TicketResultsDetailsDto result = resultChecker.getResultsForTicket(ticket, ticketDraws);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultsForEjpTicket() {
        // given
        GameType gameType = GameType.EJP;
        LocalDateTime now = LocalDateTime.now();
        String drawId = "some-draw-id";
        String ticketId = "some-ticket-id";
        String userId = "some-user-id";

        Numbers ejpNumbers1 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        Numbers ejpNumbers2 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,6))
                .extraNumbers(List.of(1,3))
                .build();
        Numbers ejpNumbers3 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,6,7))
                .extraNumbers(List.of(3,4))
                .build();
        Numbers ejpNumbers4 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,6,7,8))
                .extraNumbers(List.of(1,2))
                .build();
        Numbers ejpNumbers5 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,6,7,8,9))
                .extraNumbers(List.of(1,2))
                .build();
        Numbers ejpNumbers6 = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(6,7,8,9,10))
                .extraNumbers(List.of(1,2))
                .build();

        Numbers ticketNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();

        List<Draw> ticketDraws = new ArrayList<>();
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now.minusDays(17))
                .numbers(ejpNumbers1)
                .build());
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now.minusDays(14))
                .numbers(ejpNumbers2)
                .build());
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now.minusDays(10))
                .numbers(ejpNumbers3)
                .build());
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now.minusDays(7))
                .numbers(ejpNumbers4)
                .build());
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now.minusDays(3))
                .numbers(ejpNumbers5)
                .build());
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now)
                .numbers(ejpNumbers6)
                .build());

        Ticket ticket = Ticket.builder()
                .id(ticketId)
                .userId(userId)
                .gameType(gameType)
                .drawsAmount(6)
                .numbers(ticketNumbers)
                .generationDateTime(now.minusDays(21))
                .lastDrawDateTime(now)
                .build();

        List<TicketResultDto> ticketResultDtos = new ArrayList<>();
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ejpNumbers1)
                .level("1")
                .prize(0.0)
                .build());
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ejpNumbers2)
                .level("5")
                .prize(0.0)
                .build());
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ejpNumbers3)
                .level("10")
                .prize(0.0)
                .build());
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ejpNumbers4)
                .level("8")
                .prize(0.0)
                .build());
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ejpNumbers5)
                .level("11")
                .prize(0.0)
                .build());
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ejpNumbers6)
                .level("0")
                .prize(0.0)
                .build());

        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(ticketResultDtos)
                .totalPrize(0.0)
                .build();

        // when
        TicketResultsDetailsDto result = resultChecker.getResultsForTicket(ticket, ticketDraws);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnResultsForKenoTicket() {
        // given
        GameType gameType = GameType.KENO;
        LocalDateTime now = LocalDateTime.now();
        String drawId = "some-draw-id";
        String ticketId = "some-ticket-id";
        String userId = "some-user-id";

        Numbers kenoNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20))
                .build();
        Numbers ticketNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5))
                .build();

        List<Draw> ticketDraws = new ArrayList<>();
        ticketDraws.add(Draw.builder()
                .id(drawId)
                .type(gameType)
                .drawDateTime(now)
                .numbers(kenoNumbers)
                .build());

        Ticket ticket = Ticket.builder()
                .id(ticketId)
                .userId(userId)
                .gameType(gameType)
                .drawsAmount(1)
                .numbers(ticketNumbers)
                .generationDateTime(now.minusDays(2))
                .lastDrawDateTime(now)
                .build();

        List<TicketResultDto> ticketResultDtos = new ArrayList<>();
        ticketResultDtos.add(TicketResultDto.builder()
                .drawId(drawId)
                .ticketNumbers(ticketNumbers)
                .winningNumbers(kenoNumbers)
                .level("5;5")
                .prize(0.0)
                .build());

        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(ticketResultDtos)
                .totalPrize(0.0)
                .build();

        // when
        TicketResultsDetailsDto result = resultChecker.getResultsForTicket(ticket, ticketDraws);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }


}