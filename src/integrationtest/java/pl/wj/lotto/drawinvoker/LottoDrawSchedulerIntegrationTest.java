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

@SpringBootTest(classes = LottoApplication.class, properties = "lotto.game-type.config.interval.lotto=*/1 * * * * *")
public class LottoDrawSchedulerIntegrationTest {
    @SpyBean
    private DrawServicePort drawServicePort;

    @Test
    void shouldInvokeLottoDrawEverySpecifiedTime() {
        // given
        GameType gameType = GameType.LOTTO;
        // when && then
        await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(drawServicePort, times(1))
                        .addDraw(gameType));
    }
}
