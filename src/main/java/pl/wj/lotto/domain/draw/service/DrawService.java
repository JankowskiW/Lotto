package pl.wj.lotto.domain.draw.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeExtractor;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.ticket.model.Ticket;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class DrawService {
    private final Clock clock;
    private final DrawRepositoryPort drawRepositoryPort;


    public List<DrawResponseDto> getGameTypeDraws(int gameTypeId) {
        GameType type = GameTypeExtractor.getGameTypeById(gameTypeId);
        List<Draw> draws = drawRepositoryPort.findAllByType(type);
        return DrawMapper.toDrawResponseDtos(draws);
    }

    public DrawResponseDto addDraw(DrawRequestDto drawRequestDto) {
        Draw draw = DrawMapper.toDraw(drawRequestDto);
        draw.setDrawDateTime(LocalDateTime.now(clock));
        draw = drawRepositoryPort.save(draw);
        return DrawMapper.toDrawResponseDto(draw);
    }

    public DrawResponseDto getDraw(String id) {
        Draw draw = drawRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Draw not found"));
        return DrawMapper.toDrawResponseDto(draw);
    }

    public DrawResultDto getDrawResult(String drawId) {
        return drawRepositoryPort.findDrawResultById(drawId).orElseThrow(() -> new RuntimeException("Draw not found"));
    }

    public List<Draw> getDrawsForTicket(Ticket ticket) {
        return drawRepositoryPort.findAllByTypeAndDrawDateTime(ticket.getGameType(), ticket.getGenerationDateTime(), ticket.getLastDrawDateTime());
    }
}
