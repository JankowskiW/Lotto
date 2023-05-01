package pl.wj.lotto.domain.draw.mapper;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeExtractor;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.Numbers;
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
                .drawDateTime(draw.getDrawDateTime())
                .build();
    }

    public static Draw toDraw(DrawRequestDto drawRequestDto) {
        GameType gameType = GameTypeExtractor.getGameTypeById(drawRequestDto.typeId());
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(drawRequestDto.mainNumbers())
                .extraNumbers(drawRequestDto.extraNumbers())
                .build();
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
                .drawDateTime(drawEntity.getDrawDateTime())
                .build();
    }

    public static DrawEntity toDrawEntity(Draw draw) {
        return DrawEntity.builder()
                .id(draw.getId())
                .type(draw.getType())
                .numbers(draw.getNumbers())
                .drawDateTime(draw.getDrawDateTime())
                .build();
    }
}
