package pl.wj.lotto.infrastructure.persistence.database.ticket.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.wj.lotto.infrastructure.persistence.database.ticket.entity.TicketEntity;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<TicketEntity, String>, TicketCustomRepository {
    List<TicketEntity> getByUserId(String userId);
}
