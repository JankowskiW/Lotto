package pl.wj.lotto.domain.drawing.mapper;

import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.common.numberstemplate.model.EuroJackpotNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.KenoNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.LottoNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.Quick600Numbers;
import pl.wj.lotto.domain.drawing.model.Drawing;
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
                .typeName(drawing.getType().getName())
                .numbers(drawing.getNumbers())
                .drawingTime(drawing.getDrawingTime())
                .build();
    }

    public static Drawing toDrawing(DrawingRequestDto drawingRequestDto) {
        DrawingType type = Stream.of(DrawingType.values())
                .filter(dt -> dt.getId() == drawingRequestDto.typeId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));

        // TODO: create file in numbertemplate package and put that fragment of code in there
        NumbersTemplate numbers;
        switch(type) {
            case LOTTO -> numbers = new LottoNumbers();
            case Q600 -> numbers = new Quick600Numbers();
            case EJP -> numbers = new EuroJackpotNumbers();
            case KENO -> numbers = new KenoNumbers();
            default -> numbers = null;
        }
        numbers.setNumbers(drawingRequestDto.mainNumbers(), drawingRequestDto.extraNumbers());
        return Drawing.builder()
                .type(type)
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
