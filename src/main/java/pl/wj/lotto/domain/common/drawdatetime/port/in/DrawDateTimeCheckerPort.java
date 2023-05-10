package pl.wj.lotto.domain.common.drawdatetime.port.in;

import pl.wj.lotto.domain.common.gametype.GameType;

import java.time.LocalDateTime;

public interface DrawDateTimeCheckerPort {
    LocalDateTime getNextDrawDateTime(GameType gameType);
    LocalDateTime getNextDrawDateTimeForTicket(GameType gameType, LocalDateTime lastDrawTime);

    LocalDateTime getLastDrawDateTimeForTicket(GameType gameType, int numberOfDraws, LocalDateTime generationDateTime);
}
