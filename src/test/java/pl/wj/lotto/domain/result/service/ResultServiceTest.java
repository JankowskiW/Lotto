package pl.wj.lotto.domain.result.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.DrawResultDetailsResponseDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.ticket.port.in.TicketServicePort;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;

import java.time.Clock;
import java.time.LocalDateTime;
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
        String drawId = "some-draw-id";
        TicketResultDto ticketResultDto1 = TicketResultDto.builder()
                .drawId(drawId)
                .level("1")
                .ticketNumbers(Numbers.builder()
                        .gameType(gameType)
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .winningNumbers(Numbers.builder()
                        .gameType(gameType)
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .prize(40000000.0)
                .build();
        TicketResultDto ticketResultDto2 = TicketResultDto.builder()
                .drawId(drawId)
                .level("4")
                .ticketNumbers(Numbers.builder()
                        .gameType(gameType)
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                        .mainNumbers(List.of(1,2,3,7,8,9))
                        .build())
                .winningNumbers(Numbers.builder()
                        .gameType(gameType)
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                        .mainNumbers(List.of(1,2,3,4,5,6))
                        .build())
                .prize(225.5)
                .build();
        TicketResultsDetailsDto ticketResultsDetailsDto = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(List.of(ticketResultDto1, ticketResultDto2))
                .totalPrize(ticketResultDto1.prize() + ticketResultDto2.prize())
                .build();
        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .results(List.of(ticketResultDto1, ticketResultDto2))
                .totalPrize(ticketResultDto1.prize() + ticketResultDto2.prize())
                .build();
//        given(ticketServicePort.getTicket(anyString())).willReturn(TicketResponseDto.builder().build());
//        given(drawServicePort.getDrawsForTicket(anyString())).willReturn(List.of());
//        given(resultCheckerPort.getResultsForTicket(any(TicketResponseDto.class), anyList())).willReturn(ticketResultsDetailsDto);

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
                        .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
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