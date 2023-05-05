package pl.wj.lotto.domain.result.helper.resultchecker.port.in;

import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.util.List;
import java.util.Map;

public interface ResultCheckerPort {
    Map<Integer, Integer> getResultsForDraw(DrawResultDto drawResultDto, List<PlayerNumbersDto> playerNumbersDtos);
}
