package pl.wj.lotto.domain.draw.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.model.EuroJackpotNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.LottoNumbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;
import pl.wj.lotto.infrastructure.persistence.fake.draw.DrawFakeAdapter;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DrawServiceAdapterComponentTest {
    private DrawRepositoryPort drawRepositoryPort;
    private DrawServiceAdapter drawServiceAdapter;


    @BeforeEach
    void setUp() {
        drawRepositoryPort = new DrawFakeAdapter();
        drawServiceAdapter = new DrawServiceAdapter(new DrawService(drawRepositoryPort));
    }

    @Test
    void shouldReturnDrawResponseDtosListOfSpecificDrawType() {
        // given
        int expectedSize = 2;
        GameType gameType = GameType.EJP;
        NumbersTemplate numbersEJP = new EuroJackpotNumbers();
        numbersEJP.setNumbers(List.of(1,2,3,4,5), List.of(1,2));
        drawRepositoryPort.save(Draw.builder().type(gameType).numbers(numbersEJP).build());
        numbersEJP = new EuroJackpotNumbers();
        numbersEJP.setNumbers(List.of(1,2,3,4,5), List.of(1,3));
        drawRepositoryPort.save(Draw.builder().type(gameType).numbers(numbersEJP).build());
        NumbersTemplate numbersLotto = new LottoNumbers();
        numbersLotto.setNumbers(List.of(3,4,5,6,7,8), null);
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