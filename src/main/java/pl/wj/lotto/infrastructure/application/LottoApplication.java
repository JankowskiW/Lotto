package pl.wj.lotto.infrastructure.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"pl.wj.lotto.infrastructure.persistence.database"})
@EntityScan(basePackages = {"pl.wj.lotto.infrastructure.persistence.database"})
public class LottoApplication {
    public static void main(String[] args) {
        SpringApplication.run(LottoApplication.class, args);
    }

}
