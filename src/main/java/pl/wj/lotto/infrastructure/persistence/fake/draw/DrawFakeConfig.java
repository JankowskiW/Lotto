package pl.wj.lotto.infrastructure.persistence.fake.draw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;

@Configuration
@Profile("fake")
public class DrawFakeConfig {
    @Bean
    public DrawRepositoryPort drawRepositoryPort() {
        return new DrawFakeAdapter();
    }
}
