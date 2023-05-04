package pl.wj.lotto.infrastructure.application.rest.draw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.draw.adapter.DrawServiceAdapter;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;

import java.time.Clock;

@Configuration
public class DrawConfig {
    @Bean
    public DrawServicePort drawServicePort(Clock clock, DrawRepositoryPort drawRepositoryPort) {
        DrawService drawService = new DrawService(clock, drawRepositoryPort);
        return new DrawServiceAdapter(drawService);
    }
}
