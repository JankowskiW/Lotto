package pl.wj.lotto.domain.drawing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.drawing.mapper.DrawingMapper;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.DrawingType;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DrawingServiceTest {
    @Mock
    private DrawingRepositoryPort drawingRepositoryPort;
    @InjectMocks
    private DrawingService drawingService;

    @Test
    void shouldReturnEmptyListWhenThereIsNoDrawingWithGivenType() {
        // given
        DrawingType drawingType = DrawingType.EJP;
        given(drawingRepositoryPort.findAllByType(any(DrawingType.class))).willReturn(new ArrayList<>());

        // when
        List<DrawingResponseDto> result = drawingService.getDrawingsByType(drawingType.getId());

        // then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void shouldReturnDrawingsWithGivenType() {
        // given
        DrawingType drawingType = DrawingType.EJP;
        List<Drawing> drawings = new ArrayList<>();
        List<DrawingResponseDto> expectedResult = DrawingMapper.toDrawingResponseDtos(drawings);
        given(drawingRepositoryPort.findAllByType(any(DrawingType.class))).willReturn(drawings);

        // when
        List<DrawingResponseDto> result = drawingService.getDrawingsByType(drawingType.getId());

        // then
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldAddDrawing() {
        // given
        String id = UUID.randomUUID().toString();
        LocalDateTime drawingTime = LocalDateTime.now();
        DrawingType drawingType = DrawingType.EJP;
        DrawingRequestDto drawingRequestDto = DrawingRequestDto.builder()
                .type(drawingType.getName())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        Drawing drawing = DrawingMapper.toDrawing(drawingRequestDto);
        DrawingResponseDto expectedResult = DrawingMapper.toDrawingResponseDto(drawing);
        expectedResult.setId(id);
        expectedResult.setDrawingTime(drawingTime);
        given(drawingRepositoryPort.save(any(Drawing.class)))
                .willAnswer(
                        i -> {
                            Drawing d = i.getArgument(0, Drawing.class);
                            d.setId(id);
                            d.setDrawingTime(drawingTime);
                            return d;
                        }
                );

        // when
        DrawingResponseDto result = drawingService.addDrawing(drawingRequestDto);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawingByIdWhenExistsInDatabase() {
        // given
        String id = UUID.randomUUID().toString();
        LocalDateTime drawingTime = LocalDateTime.now();
        Drawing drawing = Drawing.builder()
                .id(id)
                .type(DrawingType.LOTTO)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .drawingTime(drawingTime)
                .build();
        DrawingResponseDto expectedResult = DrawingMapper.toDrawingResponseDto(drawing);
        given(drawingRepositoryPort.findById(anyString())).willReturn(Optional.of(drawing));

        // when
        DrawingResponseDto result = drawingService.getDrawingById(id);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenDrawingNotExistsInDabase() {
        // given
        String id = UUID.randomUUID().toString();
        given(drawingRepositoryPort.findById(anyString())).willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> drawingService.getDrawingById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Drawing not found");
    }

}