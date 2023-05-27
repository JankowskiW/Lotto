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


@SpringBootTest(classes = LottoApplication.class, properties = "lotto.game-type.config.interval.q600=*/1 * * * * *")
public class Q600DrawSchedulerIntegrationTest {
    @SpyBean
    private DrawServicePort drawServicePort;

    @Test
    void shouldInvokeQ600DrawEverySpecifiedTime() {
        // given
        GameType gameType = GameType.Q600;
        // when && then
        await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(drawServicePort, times(1))
                        .addDraw(gameType));
    }
}
