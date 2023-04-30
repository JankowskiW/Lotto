package pl.wj.lotto.domain.common.drawdatetime.port.in;

import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTime;

import java.time.LocalDateTime;

public interface DrawDateTimeCheckerPort {
    LocalDateTime getNextDrawDateTime(DrawDateTime drawDateTime);
}
