package pl.wj.lotto.infrastructure.persistence.database.ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.Numbers;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "tickets")
public class TicketEntity {
    @Id
    String id;
    String userId;
    GameType gameType;
    int numberOfDraws;
    Numbers numbers;
    LocalDateTime generationDateTime;
    LocalDateTime lastDrawDateTime;
}
