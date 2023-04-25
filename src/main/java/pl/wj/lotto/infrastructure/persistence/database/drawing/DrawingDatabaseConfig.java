package pl.wj.lotto.infrastructure.persistence.database.drawing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.drawing.repository.DrawingRepository;

@Configuration
@Profile("!mem")
public class DrawingDatabaseConfig {
    @Bean
    public DrawingRepositoryPort drawingRepositoryPort(DrawingRepository drawingRepository) {
        return new DrawingDatabaseAdapter(drawingRepository);
    }
}
