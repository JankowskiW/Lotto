package pl.wj.lotto.domain.drawing.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.model.EuroJackpotNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.LottoNumbers;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;
import pl.wj.lotto.domain.drawing.service.DrawingService;
import pl.wj.lotto.infrastructure.persistence.inmemory.drawing.DrawingInMemoryAdapter;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DrawingServiceAdapterComponentTest {
    private DrawingRepositoryPort drawingRepositoryPort;
    private DrawingServiceAdapter drawingServiceAdapter;


    @BeforeEach
    void setUp() {
        drawingRepositoryPort = new DrawingInMemoryAdapter();
        drawingServiceAdapter = new DrawingServiceAdapter(new DrawingService(drawingRepositoryPort));
    }

    @Test
    void shouldReturnDrawingResponseDtosListOfSpecificDrawingType() {
        // given
        int expectedSize = 2;
        DrawingType drawingType = DrawingType.EJP;
        NumbersTemplate numbersEJP = new EuroJackpotNumbers();
        numbersEJP.setNumbers(List.of(1,2,3,4,5), List.of(1,2));
        drawingRepositoryPort.save(Drawing.builder().type(drawingType).numbers(numbersEJP).build());
        numbersEJP = new EuroJackpotNumbers();
        numbersEJP.setNumbers(List.of(1,2,3,4,5), List.of(1,3));
        drawingRepositoryPort.save(Drawing.builder().type(drawingType).numbers(numbersEJP).build());
        NumbersTemplate numbersLotto = new LottoNumbers();
        numbersLotto.setNumbers(List.of(3,4,5,6,7,8), null);
        drawingRepositoryPort.save(Drawing.builder().type(DrawingType.LOTTO).numbers(numbersLotto).build());
        List<String> unwantedDrawingTypes =
                Stream.of(DrawingType.values())
                        .filter(dt -> !dt.equals(drawingType))
                        .map(DrawingType::getName)
                        .toList();

        // when
        List<DrawingResponseDto> result = drawingServiceAdapter.getDrawingsByTypeId(drawingType.getId());

        // then
        assertAll(
                () -> assertThat(result).isNotNull().hasSize(expectedSize),
                () -> assertThat(result.stream().map(DrawingResponseDto::typeName).toList())
                        .doesNotContainAnyElementsOf(unwantedDrawingTypes)
        );
    }


}