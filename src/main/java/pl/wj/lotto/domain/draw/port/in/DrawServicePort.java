package pl.wj.lotto.domain.draw.port.in;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.ticket.model.Ticket;

import java.util.List;

public interface DrawServicePort {
    List<DrawResponseDto> getGameTypeDraws(int gameTypeId);

    DrawResponseDto getDraw(String id);

    DrawResultDto getDrawResult(String drawId);

    List<Draw> getDrawsForTicket(Ticket ticket);

    DrawResponseDto addDraw(GameType gameType);
}
