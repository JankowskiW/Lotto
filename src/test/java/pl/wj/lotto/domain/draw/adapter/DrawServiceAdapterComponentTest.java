package pl.wj.lotto.domain.draw.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawdatetime.DrawDateTimeChecker;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;
import pl.wj.lotto.infrastructure.clock.config.ClockFakeConfig;
import pl.wj.lotto.infrastructure.persistence.fake.draw.DrawFakeAdapter;

import java.time.Clock;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DrawServiceAdapterComponentTest {
    private Clock clock;
    private DrawDateTimeChecker drawDateTimeChecker;
    private DrawRepositoryPort drawRepositoryPort;
    private DrawServiceAdapter drawServiceAdapter;


    @BeforeEach
    void setUp() {
        clock = new ClockFakeConfig().clock();
        drawDateTimeChecker = new DrawDateTimeChecker(clock);
        drawRepositoryPort = new DrawFakeAdapter();
        drawServiceAdapter = new DrawServiceAdapter(new DrawService(drawRepositoryPort, drawDateTimeChecker));
    }

    @Test
    void shouldReturnDrawResponseDtosListOfSpecificDrawType() {
        // given
        int expectedSize = 2;
        GameType gameType = GameType.EJP;
        Numbers numbersEJP = Numbers.builder()
                .gameType(gameType)
                .drawDateTime(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTime())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        drawRepositoryPort.save(Draw.builder().type(gameType).numbers(numbersEJP).build());
        numbersEJP = Numbers.builder()
                .gameType(gameType)
                .drawDateTime(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTime())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,3))
                .build();
        drawRepositoryPort.save(Draw.builder().type(gameType).numbers(numbersEJP).build());
        Numbers numbersLotto = Numbers.builder()
                .gameType(GameType.LOTTO)
                .drawDateTime(GameTypeSettingsContainer.getGameTypeSettings(GameType.LOTTO).drawDateTime())
                .mainNumbers(List.of(1,2,3,4,5,8))
                .build();
        drawRepositoryPort.save(Draw.builder().type(GameType.LOTTO).numbers(numbersLotto).build());
        List<String> unwantedDrawTypes =
                Stream.of(GameType.values())
                        .filter(dt -> !dt.equals(gameType))
                        .map(GameType::getName)
                        .toList();

        // when
        List<DrawResponseDto> result = drawServiceAdapter.getDrawsByTypeId(gameType.getId());

        // then
        assertAll(
                () -> assertThat(result).isNotNull().hasSize(expectedSize),
                () -> assertThat(result.stream().map(DrawResponseDto::typeName).toList())
                        .doesNotContainAnyElementsOf(unwantedDrawTypes)
        );
    }


}