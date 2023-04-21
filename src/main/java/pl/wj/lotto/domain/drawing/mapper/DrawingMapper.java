package pl.wj.lotto.domain.drawing.mapper;

import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.common.DrawingType.DrawingType;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.drawing.entity.DrawingEntity;

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
                .filter(dt -> dt.getName().equals(drawingRequestDto.type()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
        return Drawing.builder()
                .type(type)
                .mainNumbers(drawingRequestDto.mainNumbers())
                .extraNumbers(drawingRequestDto.extraNumbers())
                .build();
    }

    public static List<Drawing> toDrawings(List<DrawingEntity> drawingEntities) {
        return drawingEntities.stream().map(DrawingMapper::toDrawing).collect(Collectors.toList());
    }

    public static Drawing toDrawing(DrawingEntity drawingEntity) {
        return Drawing.builder()
                .id(drawingEntity.getId())
                .type(null)
                .drawingTime(drawingEntity.getDrawingTime())
                .build();
    }

    public static DrawingEntity toDrawingEntity(Drawing drawing) {
        return DrawingEntity.builder()
                .id(drawing.getId())
                .typeId(drawing.getType().getId())
                .drawingTime(drawing.getDrawingTime())
                .build();
    }
}
