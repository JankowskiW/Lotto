package pl.wj.lotto.infrastructure.drawinitiator.lotto;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;

@Component
@AllArgsConstructor
@Log4j2
public class LottoDrawScheduler {
    private final DrawServicePort drawServicePort;

    @Scheduled(cron = "")
    public void initiateLottoDraw() {

    }
}
