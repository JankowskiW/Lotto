package pl.wj.lotto.domain.draw.port.in;

import org.springframework.data.domain.Page;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.domain.ticket.model.Ticket;

import java.util.List;

public interface DrawServicePort {
    Page<DrawResponseDto> getGameTypeDraws(int gameTypeId, int pageNumber, int pageSize);

    DrawResponseDto addDraw(GameType gameType);

    DrawResponseDto getDraw(String id);

    DrawWinningNumbersDto getDrawWinningNumbers(String drawId);

    List<Draw> getDrawsForTicket(Ticket ticket);
}
