package pl.wj.lotto.infrastructure.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"pl.wj.lotto.infrastructure.persistence.database"})
@Profile("!fake")
public class MongoConfig {
}
