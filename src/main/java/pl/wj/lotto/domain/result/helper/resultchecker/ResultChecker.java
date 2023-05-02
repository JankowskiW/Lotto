package pl.wj.lotto.domain.result.helper.resultchecker;

import pl.wj.lotto.domain.result.helper.resultchecker.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;

public class ResultChecker implements ResultCheckerPort {

    @Override
    public DrawResultDto getResultByDrawId(String drawId) {
        return new DrawResultDto();
    }
}
