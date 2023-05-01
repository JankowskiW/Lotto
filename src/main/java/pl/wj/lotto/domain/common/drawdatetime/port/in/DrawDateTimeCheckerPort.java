package pl.wj.lotto.domain.common.drawdatetime.port.in;

import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;

import java.time.LocalDateTime;

public interface DrawDateTimeCheckerPort {
    LocalDateTime getNextDrawDateTime(DrawDateTimeSettings drawDateTimeSettings);
    LocalDateTime getNextDrawDateTimeForTicket(
            DrawDateTimeSettings drawDateTimeSettings, LocalDateTime generationDateTime);
}
