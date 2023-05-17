package pl.wj.lotto.domain.result.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.infrastructure.application.clock.config.ClockFakeConfig;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {
    @Mock
    private ResultCheckerPort resultCheckerPort;
    @Mock
    private TicketServicePort ticketServicePort;
    @Mock
    private DrawServicePort drawServicePort;
    @InjectMocks
    private ResultService resultService;

    private final Clock clock = new ClockFakeConfig().clock();


    @Test
    void shouldReturnResultForTicket() {
        // given
        GameType gameType = GameType.LOTTO;
        String ticketId = "some-ticket-id";
        String userId = "some-user-id";
        double prize1 = 1000000;
        double prize2 = 2000;
        Numbers ticketNumbers = Numbers.builder()
                .gameType(gameType)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        Ticket ticket = Ticket.builder()
                .id(ticketId)
                .userId(userId)
                .gameType(gameType)
                .drawsAmount(2)
                .numbers(ticketNumbers)
                .generationDateTime(LocalDateTime.now(clock).minusDays(7))
                .lastDrawDateTime(LocalDateTime.now(clock).minusHours(1))
                .build();
        List<Draw> ticketDraws = new ArrayList<>();
        ticketDraws.add(Draw.builder()
                        .id("some-draw-id-1")
                        .type(gameType)
                        .numbers(Numbers.builder()
                                .gameType(gameType)
                                .mainNumbers(List.of(1,2,3,4,5,6))
                                .build())
                        .drawDateTime(LocalDateTime.now(clock).minusDays(6))
                .build());
        ticketDraws.add(Draw.builder()
                .id("some-draw-id-2")
                .type(gameType)
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(List.of(1,2,3,7,8,9))
                        .build())
                .drawDateTime(LocalDateTime.now(clock).minusDays(2))
                .build());
        List<TicketResultDto> ticketResults = new ArrayList<>();
        ticketResults.add(TicketResultDto.builder()
                .drawId(ticketDraws.get(0).getId())
                .level("1")
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ticketDraws.get(0).getNumbers())
                .prize(prize1)
                .build());
        ticketResults.add(TicketResultDto.builder()
                .drawId(ticketDraws.get(1).getId())
                .level("4")
                .ticketNumbers(ticketNumbers)
                .winningNumbers(ticketDraws .get(1).getNumbers())
                .prize(prize2)
                .build());
        TicketResultsDetailsDto ticketResultsDetailsDto = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(ticketResults)
                .totalPrize(ticketResults.get(0).prize() + ticketResults.get(1).prize())
                .build();
        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(ticketResults)
                .totalPrize(prize1 + prize2)
                .build();
        given(ticketServicePort.getTicket(anyString())).willReturn(ticket);
        given(drawServicePort.getDrawsForTicket(any(Ticket.class))).willReturn(ticketDraws);
        given(resultCheckerPort.getResultsForTicket(any(Ticket.class), anyList())).willReturn(ticketResultsDetailsDto);

        // when
        TicketResultsDetailsDto result = resultService.getTicketResults(ticketId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawResultDetails() {
        // given
        String drawId = "some-draw-id";
        GameType gameType = GameType.LOTTO;
        DrawResultDto drawResultDto = DrawResultDto.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now(clock))
                .numbers(Numbers.builder()
                        .gameType(gameType)
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .build();
        Map<String,Integer> checkerResults = new HashMap<>();
        checkerResults.put("1", 1);
        checkerResults.put("2", 5);
        checkerResults.put("3", 2);
        checkerResults.put("4", 10);
        DrawResultDetailsResponseDto expectedResult = DrawResultDetailsResponseDto.builder()
                .drawId(drawId)
                .drawDateTime(drawResultDto.drawDateTime())
                .results(checkerResults)
                .build();
        given(drawServicePort.getDrawResult(anyString())).willReturn(drawResultDto);
        given(ticketServicePort.getPlayersDrawNumbers(any(DrawResultDto.class))).willReturn(List.of());
        given(resultCheckerPort.getResultsForDraw(any(DrawResultDto.class), anyList())).willReturn(checkerResults);

        // when
        DrawResultDetailsResponseDto result = resultService.getDrawResultDetails(drawId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

}