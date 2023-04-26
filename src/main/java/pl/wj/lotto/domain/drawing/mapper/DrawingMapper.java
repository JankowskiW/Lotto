package pl.wj.lotto.domain.drawing.mapper;

import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.drawingtype.DrawingTypeExtractor;
import pl.wj.lotto.domain.common.numberstemplate.NumberTemplateCreator;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.drawing.entity.DrawingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DrawingMapper {
    public static List<DrawingResponseDto> toDrawingResponseDtos(List<Drawing> drawings) {
        return drawings.stream().map(DrawingMapper::toDrawingResponseDto).collect(Collectors.toList());
    }

    public static DrawingResponseDto toDrawingResponseDto(Drawing drawing) {
        return DrawingResponseDto.builder()
                .id(drawing.getId())
                .typeName(drawing.getType().getName())
                .numbers(drawing.getNumbers())
                .drawingTime(drawing.getDrawingTime())
                .build();
    }

    public static Drawing toDrawing(DrawingRequestDto drawingRequestDto) {
        DrawingType drawingType = DrawingTypeExtractor.getDrawingTypeById(drawingRequestDto.typeId());
        NumbersTemplate numbers = NumberTemplateCreator.createNumbersTemplateByDrawingType(drawingType);
        numbers.setNumbers(drawingRequestDto.mainNumbers(), drawingRequestDto.extraNumbers());
        return Drawing.builder()
                .type(drawingType)
                .numbers(numbers)
                .build();
    }

    public static List<Drawing> toDrawings(List<DrawingEntity> drawingEntities) {
        return drawingEntities.stream().map(DrawingMapper::toDrawing).collect(Collectors.toList());
    }

    public static Drawing toDrawing(DrawingEntity drawingEntity) {
        return Drawing.builder()
                .id(drawingEntity.getId())
                .type(drawingEntity.getType())
                .numbers(drawingEntity.getNumbers())
                .drawingTime(drawingEntity.getDrawingTime())
                .build();
    }

    public static DrawingEntity toDrawingEntity(Drawing drawing) {
        return DrawingEntity.builder()
                .id(drawing.getId())
                .type(drawing.getType())
                .numbers(drawing.getNumbers())
                .drawingTime(drawing.getDrawingTime())
                .build();
    }
}
