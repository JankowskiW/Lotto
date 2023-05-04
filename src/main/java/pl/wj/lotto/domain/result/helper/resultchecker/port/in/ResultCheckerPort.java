package pl.wj.lotto.domain.result.helper.resultchecker.port.in;

import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.model.dto.ResultDto;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.util.List;

public interface ResultCheckerPort {
    List<ResultDto> getResultsForDraw(DrawResultDto drawResultDto, List<PlayerNumbersDto> playerNumbersDtos);
}
