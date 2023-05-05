package pl.wj.lotto.infrastructure.persistence.database.ticket.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketCustomRepositoryImpl implements TicketCustomRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<TicketEntity> getPlayersDrawNumbersByGameTypeAndLastDrawDateTime(GameType type, LocalDateTime drawDateTime) {
        Query query = new Query();
        System.out.println(drawDateTime);
        query.addCriteria(
                Criteria.where("lastDrawDateTime").gte(drawDateTime)
        );
        query.fields()
                .include("userId")
                .include("gameType")
                .include("numbers");

        return mongoTemplate.find(query, TicketEntity.class);
    }
}
