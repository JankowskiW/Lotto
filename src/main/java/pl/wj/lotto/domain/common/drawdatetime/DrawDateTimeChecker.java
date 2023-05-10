package pl.wj.lotto.domain.common.drawdatetime;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
public class DrawDateTimeChecker implements DrawDateTimeCheckerPort {
    private final Clock clock;
    private final GameTypeSettingsContainer gameTypeSettingsContainer;

    @Override
    public LocalDateTime getNextDrawDateTime(GameType gameType) {
        TriggerContext triggerContext = new SimpleTriggerContext(clock);
        CronTrigger trigger = new CronTrigger(gameTypeSettingsContainer.intervals().get(gameType));
        Instant nextDrawDateTime = trigger.nextExecution(triggerContext);
        return LocalDateTime.ofInstant(nextDrawDateTime, clock.getZone());
    }

    @Override
    public LocalDateTime getNextDrawDateTimeForTicket(GameType gameType, LocalDateTime lastDrawTime) {
        LocalDateTime nextDrawDateTime = getNextDrawDateTime(gameType);
        return nextDrawDateTime.isBefore(lastDrawTime) ? nextDrawDateTime : lastDrawTime;
    }

    @Override
    public LocalDateTime getLastDrawDateTimeForTicket(GameType gameType, int numberOfDraws, LocalDateTime generationDateTime) {
        if (numberOfDraws == 0) return generationDateTime;
        String cronExpression = gameTypeSettingsContainer.intervals().get(gameType);
        SimpleTriggerContext triggerContext = new SimpleTriggerContext(clock);
        Instant generationInstant = generationDateTime.toInstant(ZoneOffset.of(clock.getZone().getId()));
        triggerContext.update(null, null, generationInstant);
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        LocalDateTime nextDrawTime = LocalDateTime.ofInstant(cronTrigger.nextExecution(triggerContext), clock.getZone());
        return getLastDrawDateTimeForTicket(gameType, numberOfDraws - 1, nextDrawTime);
    }
}
