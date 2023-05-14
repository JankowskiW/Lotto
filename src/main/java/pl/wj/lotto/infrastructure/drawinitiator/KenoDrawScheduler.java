package pl.wj.lotto.infrastructure.drawinitiator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;

@Component
@RequiredArgsConstructor
@Log4j2
public class KenoDrawScheduler {
    private final DrawServicePort drawServicePort;

    @Scheduled(cron = "${lotto.game-type.config.interval.keno}")
    public void initiateKenoDraw() {
        log.info("Keno draw initiated");
        drawServicePort.addDraw(GameType.KENO);
    }
}
