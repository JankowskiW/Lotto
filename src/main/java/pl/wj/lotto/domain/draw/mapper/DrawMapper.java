package pl.wj.lotto.domain.draw.mapper;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeExtractor;
import pl.wj.lotto.domain.common.numberstemplate.NumberTemplateCreator;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DrawMapper {
    public static List<DrawResponseDto> toDrawResponseDtos(List<Draw> draws) {
        return draws.stream().map(DrawMapper::toDrawResponseDto).collect(Collectors.toList());
    }

    public static DrawResponseDto toDrawResponseDto(Draw draw) {
        return DrawResponseDto.builder()
                .id(draw.getId())
                .typeName(draw.getType().getName())
                .numbers(draw.getNumbers())
                .drawTime(draw.getDrawTime())
                .build();
    }

    public static Draw toDraw(DrawRequestDto drawRequestDto) {
        GameType gameType = GameTypeExtractor.getGameTypeById(drawRequestDto.typeId());
        NumbersTemplate numbers = NumberTemplateCreator.createNumbersTemplateByGameType(gameType);
        numbers.setNumbers(drawRequestDto.mainNumbers(), drawRequestDto.extraNumbers());
        return Draw.builder()
                .type(gameType)
                .numbers(numbers)
                .build();
    }

    public static List<Draw> toDraws(List<DrawEntity> drawEntities) {
        return drawEntities.stream().map(DrawMapper::toDraw).collect(Collectors.toList());
    }

    public static Draw toDraw(DrawEntity drawEntity) {
        return Draw.builder()
                .id(drawEntity.getId())
                .type(drawEntity.getType())
                .numbers(drawEntity.getNumbers())
                .drawTime(drawEntity.getDrawTime())
                .build();
    }

    public static DrawEntity toDrawEntity(Draw draw) {
        return DrawEntity.builder()
                .id(draw.getId())
                .type(draw.getType())
                .numbers(draw.getNumbers())
                .drawTime(draw.getDrawTime())
                .build();
    }
}
