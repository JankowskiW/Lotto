package pl.wj.lotto.infrastructure.persistence.database.draw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.draw.repository.DrawRepository;

@Configuration
@Profile("!in-memory")
public class DrawDatabaseConfig {
    @Bean
    public DrawRepositoryPort drawRepositoryPort(DrawRepository drawRepository) {
        return new DrawDatabaseAdapter(drawRepository);
    }
}
