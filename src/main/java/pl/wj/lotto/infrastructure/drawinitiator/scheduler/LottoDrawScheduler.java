package pl.wj.lotto.infrastructure.drawinitiator.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;

@Component
@Log4j2
@RequiredArgsConstructor
public class LottoDrawScheduler {
    private final DrawServicePort drawServicePort;

    @Scheduled(cron = "${lotto.draw-initiator.config.interval.lotto}")
    public void initiateLottoDraw() {
    }
}
