package pl.wj.lotto.infrastructure.application.rest.draw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wj.lotto.domain.common.drawdatetime.DrawDateTimeChecker;
import pl.wj.lotto.domain.draw.adapter.DrawServiceAdapter;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.draw.service.DrawService;

@Configuration
public class DrawConfig {
    @Bean
    public DrawServicePort drawServicePort(DrawRepositoryPort drawRepositoryPort, DrawDateTimeChecker drawDateTimeChecker) {
        DrawService drawService = new DrawService(drawRepositoryPort, drawDateTimeChecker);
        return new DrawServiceAdapter(drawService);
    }
}
