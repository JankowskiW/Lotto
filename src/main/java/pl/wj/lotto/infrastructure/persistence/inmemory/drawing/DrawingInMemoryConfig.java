package pl.wj.lotto.infrastructure.persistence.inmemory.drawing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

@Configuration
@Profile("mem")
public class DrawingInMemoryConfig {
    @Bean
    public DrawingRepositoryPort drawingRepositoryPort() {
        return new DrawingInMemoryAdapter();
    }
}
