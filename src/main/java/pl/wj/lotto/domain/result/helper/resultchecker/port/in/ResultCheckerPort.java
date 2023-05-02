package pl.wj.lotto.domain.result.helper.resultchecker.port.in;

import pl.wj.lotto.domain.result.helper.resultchecker.model.dto.DrawResultDto;

public interface ResultCheckerPort {
    DrawResultDto getResultByDrawId(String drawId);
}
