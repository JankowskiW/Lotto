package pl.wj.lotto.domain.draw.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.service.DrawService;
import pl.wj.lotto.domain.ticket.model.Ticket;

import java.util.List;

@RequiredArgsConstructor
public class DrawServiceAdapter implements DrawServicePort {
    private final DrawService drawService;

    @Override
    public List<DrawResponseDto> getGameTypeDraws(int gameTypeId) {
        return drawService.getGameTypeDraws(gameTypeId);
    }

    @Override
    public DrawResponseDto addDraw(GameType gameType) {
        return drawService.addDraw(gameType);
    }

    @Override
    public DrawResponseDto getDraw(String id) {
        return drawService.getDraw(id);
    }

    @Override
    public DrawResultDto getDrawResult(String drawId) {
        return drawService.getDrawResult(drawId);
    }

    @Override
    public List<Draw> getDrawsForTicket(Ticket ticket) {
        return drawService.getDrawsForTicket(ticket);
    }

}
