package pl.wj.lotto.drawinvoker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.infrastructure.application.LottoApplication;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = LottoApplication.class, properties = "lotto.game-type.config.interval.keno=*/1 * * * * *")
public class KenoDrawSchedulerIntegrationTest {
    @SpyBean
    private DrawServicePort drawServicePort;

    @Test
    void shouldInvokeKenoDrawEverySpecifiedTime() {
        // given
        GameType gameType = GameType.KENO;
        // when && then
        await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(drawServicePort, times(1))
                        .addDraw(gameType));
    }
}
