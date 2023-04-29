package pl.wj.lotto.domain.draw.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeExtractor;
import pl.wj.lotto.domain.common.drawtime.DrawTimeCheckable;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class DrawService {
    private final DrawRepositoryPort drawRepositoryPort;


    public List<DrawResponseDto> getDrawsByTypeId(int gameTypeId) {
        GameType type = GameTypeExtractor.getGameTypeById(gameTypeId);
        List<Draw> draws = drawRepositoryPort.findAllByType(type);
        return DrawMapper.toDrawResponseDtos(draws);
    }

    public DrawResponseDto addDraw(DrawRequestDto drawRequestDto) {
        Draw draw = DrawMapper.toDraw(drawRequestDto);
        draw = drawRepositoryPort.save(draw);
        return DrawMapper.toDrawResponseDto(draw);
    }

    public DrawResponseDto getDrawById(String id) {
        Draw draw = drawRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Draw not found"));
        return DrawMapper.toDrawResponseDto(draw);
    }

    public LocalDateTime getNextDrawTime(DrawTimeCheckable drawTimeCheckable) {
        return drawTimeCheckable.getNextDrawTime();
    }
}
