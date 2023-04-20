package pl.wj.lotto.infrastructure.application.rest.drawing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.drawing.adapter.DrawingServiceAdapter;
import pl.wj.lotto.domain.drawing.port.in.DrawingServicePort;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;
import pl.wj.lotto.domain.drawing.service.DrawingService;
import pl.wj.lotto.infrastructure.persistence.inmemory.drawing.DrawingInMemoryAdapter;

@Configuration
public class DrawingConfig {
    @Bean
    public DrawingServicePort drawingServicePort(DrawingRepositoryPort drawingRepositoryPort) {
        DrawingService drawingService = new DrawingService(drawingRepositoryPort);
        return new DrawingServiceAdapter(drawingService);
    }

    @Bean
    public DrawingRepositoryPort drawingRepositoryPort() {
        return new DrawingInMemoryAdapter();
    }
}
