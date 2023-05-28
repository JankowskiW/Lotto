package pl.wj.lotto.domain.draw.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeParser;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
        GameType gameType = GameTypeParser.getGameTypeById(drawRequestDto.typeId());
        Numbers numbers = Numbers.builder()
                .gameType(gameType)
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

    public static DrawWinningNumbersDto toDrawResultDto(Draw draw) {
        return DrawWinningNumbersDto.builder()
                .type(draw.getType())
                .drawDateTime(draw.getDrawDateTime())
                .numbers(draw.getNumbers())
                .build();
    }

    public static Page<Draw> toDrawsPage(Page<DrawEntity> drawEntitiesPage) {
        return drawEntitiesPage.map(DrawMapper::toDraw);
    }

    public static Page<DrawResponseDto> toDrawResponseDtosPage(Page<Draw> drawsPage) {
        return drawsPage.map(DrawMapper::toDrawResponseDto);
    }
}
