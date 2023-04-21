package pl.wj.lotto.domain.drawing.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.DrawingType.DrawingType;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;
import pl.wj.lotto.domain.drawing.service.DrawingService;
import pl.wj.lotto.infrastructure.persistence.inmemory.drawing.DrawingInMemoryAdapter;

import java.util.List;
import java.util.stream.Collectors;
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
    void shouldReturnDrawingResponseDtosList() {
        // given
        int expectedSize = 2;
        DrawingType drawingType = DrawingType.EJP;
        Drawing drawing = new Drawing(null, drawingType, List.of(1,2,3,4,5), List.of(1,2), null);
        drawingRepositoryPort.save(drawing);
        drawing = new Drawing(null, drawingType, List.of(1,2,3,4,6), List.of(1,3), null);
        drawingRepositoryPort.save(drawing);
        drawing = new Drawing(null, DrawingType.LOTTO, List.of(1,2,3,4,6,8), null, null);
        drawingRepositoryPort.save(drawing);
        List<String> unwantedDrawingTypes =
                Stream.of(DrawingType.values())
                        .filter(dt -> !dt.equals(drawingType))
                        .map(DrawingType::getName)
                        .toList();

        // when
        List<DrawingResponseDto> result = drawingServiceAdapter.getDrawingsByType(drawingType.getId());

        // then
        assertAll(
                () -> assertThat(result).isNotNull().hasSize(expectedSize),
                () -> {
                    assert result != null;
                    assertThat(result.stream().map(DrawingResponseDto::type).collect(Collectors.toList()))
                            .doesNotContainAnyElementsOf(unwantedDrawingTypes);
                }
        );
    }


}