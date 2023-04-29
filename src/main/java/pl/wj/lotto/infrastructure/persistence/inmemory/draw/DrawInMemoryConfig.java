package pl.wj.lotto.infrastructure.persistence.inmemory.draw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;

@Configuration
@Profile("in-memory")
public class DrawInMemoryConfig {
    @Bean
    public DrawRepositoryPort drawRepositoryPort() {
        return new DrawInMemoryAdapter();
    }
}
