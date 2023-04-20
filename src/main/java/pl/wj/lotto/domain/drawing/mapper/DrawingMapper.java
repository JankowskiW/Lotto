package pl.wj.lotto.domain.drawing.mapper;

import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.DrawingType;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DrawingMapper {
    public static List<DrawingResponseDto> toDrawingResponseDtos(List<Drawing> drawings) {
        return drawings.stream().map(DrawingMapper::toDrawingResponseDto).collect(Collectors.toList());
    }

    public static DrawingResponseDto toDrawingResponseDto(Drawing drawing) {
        return DrawingResponseDto.builder()
                .id(drawing.getId())
                .type(drawing.getType().getName())
                .extraNumbers(drawing.getExtraNumbers())
                .mainNumbers(drawing.getMainNumbers())
                .drawingTime(drawing.getDrawingTime())
                .build();
    }

    public static Drawing toDrawing(DrawingRequestDto drawingRequestDto) {
        DrawingType type = Stream.of(DrawingType.values())
                .filter(dt -> dt.getName().equals(drawingRequestDto.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
        return Drawing.builder()
                .type(type)
                .mainNumbers(drawingRequestDto.getMainNumbers())
                .extraNumbers(drawingRequestDto.getExtraNumbers())
                .build();
    }
}
