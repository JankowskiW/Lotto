package pl.wj.lotto.infrastructure.persistence.database.draw.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.Numbers;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "draws")
public class DrawEntity {
    @Id
    String id;
    GameType type;
    Numbers numbers;
    LocalDateTime drawDateTime;
}