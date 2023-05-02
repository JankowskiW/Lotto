package pl.wj.lotto.domain.draw.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;
import pl.wj.lotto.infrastructure.persistence.fake.draw.DrawFakeAdapter;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DrawServiceAdapterComponentTest {
    private DrawRepositoryPort drawRepositoryPort;
    private DrawServicePort drawServicePort;


    @BeforeEach
    void setUp() {
        drawRepositoryPort = new DrawFakeAdapter();
        drawServicePort = new DrawServiceAdapter(new DrawService(drawRepositoryPort));
    }

    @Test
    void shouldReturnDrawResponseDtosListOfSpecificDrawType() {
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


}